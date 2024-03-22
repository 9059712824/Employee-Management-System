package com.learning.employemanagementsystem.service.impl;

import com.learning.employemanagementsystem.dto.AddEmployeeDto;
import com.learning.employemanagementsystem.dto.UpdateLeavingDate;
import com.learning.employemanagementsystem.entity.JobTitleType;
import com.learning.employemanagementsystem.entity.Profile;
import com.learning.employemanagementsystem.entity.ProfileStatus;
import com.learning.employemanagementsystem.exception.AlreadyFoundException;
import com.learning.employemanagementsystem.exception.InvalidInputException;
import com.learning.employemanagementsystem.exception.NotFoundException;
import com.learning.employemanagementsystem.mapper.EmployeeMapper;
import com.learning.employemanagementsystem.entity.Employee;
import com.learning.employemanagementsystem.repository.EmployeeRepository;
import com.learning.employemanagementsystem.repository.ProfileRepository;
import com.learning.employemanagementsystem.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ProfileRepository profileRepository;

    private final EmployeeMapper employeeMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final String ADD = "add";
    private static final String REMOVE = "remove";

    @Override
    public AddEmployeeDto add(AddEmployeeDto employeeDto) {
        if (Boolean.TRUE.equals(employeeRepository.existsByEmail(employeeDto.getEmail()))) {
            throw new AlreadyFoundException("Email already exists: " + employeeDto.getEmail());
        }
        var period = Period.between(employeeDto.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now());
        employeeDto.setAge(period.getYears());
        var employee = employeeMapper.addEmployeeDtoToEmployee(employeeDto);
        employee.setPassword(passwordEncoder.encode(generateRandomPassword()));
        employee.setIsManager(Boolean.FALSE);

        var updatedEmployee = employeeRepository.save(employee);

        var profile = Profile.builder()
                .profileStatus(ProfileStatus.PENDING)
                .jobTitle(JobTitleType.ENGINEER_TRAINEE)
                .employee(updatedEmployee)
                .build();

        employee.setProfile(profile);

        profileRepository.save(profile);
        return employeeMapper.EmployeeToAddEmployeeDto(employee);
    }

    @Override
    public Employee getById(UUID id) {
        var employee = employeeRepository.findById(id);

        if (employee.isEmpty()) {
            throw new NotFoundException("employee not found with id: " + id);
        }
        return employee.get();
    }

    @Override
    public void updateLeavingDate(UUID id, UpdateLeavingDate updateLeavingDate) {
        var employee = getById(id);
        employee.setLeavingDate(updateLeavingDate.getLeavingDate());
        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> viewAll() {
        return employeeRepository.findAll();
    }

    public void managerAccess(MultipartFile file) throws IOException {
        List<List<String>> rowValues = fileProcess(file);

        for (List<String> rowValue : rowValues) {
            if (rowValue.size() != 2) {
                continue;
            }

            String email = rowValue.get(0);
            String action = rowValue.get(1);

            if (StringUtils.isNotBlank(action) && StringUtils.isNotBlank(email)) {
                Employee employee = employeeRepository.getByEmail(email);

                if (ADD.equalsIgnoreCase(action)) {
                    employee.setIsManager(Boolean.TRUE);
                } else if (REMOVE.equalsIgnoreCase(action)) {
                    var colleaguesByManager = getByManagerUuid(employee.getUuid());
                    if (!colleaguesByManager.isEmpty()) {
                        throw new InvalidInputException("Colleagues exists under this manager, Please update their manager details to proceed");
                    }
                    employee.setIsManager(Boolean.FALSE);
                }

                employeeRepository.save(employee);
            }
        }
    }

    @Override
    public void updateManagerId(MultipartFile file) throws IOException {
        List<List<String>> rowDatas = fileProcess(file);
        for (List<String> rowData : rowDatas) {
            if (rowData.size() != 3) {
                continue;
            }
            String employeeEmail = rowData.get(0);
            String managerEmail = rowData.get(1);
            String action = rowData.get(2);
            if (StringUtils.isNotBlank(employeeEmail) && StringUtils.isNotBlank(managerEmail) && StringUtils.isNotBlank(action)) {
                existsByEmail(employeeEmail);
                existsByEmail(managerEmail);
                var employee = getByEmail(employeeEmail);
                var manager = getByEmail(managerEmail);
                if (ADD.equalsIgnoreCase(action)) {
                    if (manager.getIsManager().equals(Boolean.FALSE)) {
                        throw new InvalidInputException("Don't have manager access for email" + managerEmail);
                    }
                    employee.setManager(manager);
                    employeeRepository.save(employee);
                } else if (REMOVE.equalsIgnoreCase(action)) {
                    if (manager.getUuid().equals(employee.getManager().getUuid())) {
                        employee.setManager(null);
                        employeeRepository.save(employee);
                    } else {
                        throw new InvalidInputException("Invalid Manager details provided");
                    }
                }
            }
        }
    }

    @Override
    public List<Employee> getByManagerUuid(UUID managerId) {
        var employee = getById(managerId);
        if (employee.getIsManager().equals(Boolean.TRUE)) {
            return employeeRepository.getAllByManagerUuid(managerId);
        } else {
            throw new NotFoundException("User don't have manager access");
        }
    }

    public Employee getByEmail(String email) {
        var employee = employeeRepository.getByEmail(email);

        if (employee == null) {
            throw new NotFoundException("Employee not found with email " + email);
        }
        return employee;
    }

    public void existsByEmail(String email) {
        var isEmployeeExists = employeeRepository.existsByEmail(email);
        if (Boolean.FALSE.equals(isEmployeeExists)) {
            throw new NotFoundException("Employee not Exists with email " + email);
        }
    }

    public List<List<String>> fileProcess(MultipartFile file) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new InvalidInputException("Sheet Not Found");
            }

            Row headerRow = sheet.getRow(0);
            List<String> columnHeadings = new ArrayList<>();
            DataFormatter dataFormatter = new DataFormatter();

            Iterator<Cell> headerCellIterator = headerRow.cellIterator();
            while (headerCellIterator.hasNext()) {
                Cell cell = headerCellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                columnHeadings.add(cellValue);
            }

            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            List<List<String>> rowValues = new ArrayList<>();
            while (rowIterator.hasNext()) {
                Row dataRow = rowIterator.next();
                List<String> rowValue = new ArrayList<>();

                Iterator<Cell> dataCellIterator = dataRow.cellIterator();
                while (dataCellIterator.hasNext()) {
                    Cell cell = dataCellIterator.next();
                    String cellValue = dataFormatter.formatCellValue(cell);
                    if (StringUtils.isNotBlank(cellValue)) {
                        rowValue.add(cellValue);
                    } else {
                        rowValue.add("");
                    }
                }
                rowValues.add(rowValue);
            }
            return rowValues;
        }
    }


    public String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < sb.capacity(); i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }
}
