package com.learning.employemanagementsystem.service.Impl;

import com.learning.employemanagementsystem.dto.AddEmployeeDto;
import com.learning.employemanagementsystem.dto.UpdateLeavingDate;
import com.learning.employemanagementsystem.entity.JobTitleType;
import com.learning.employemanagementsystem.entity.Profile;
import com.learning.employemanagementsystem.entity.ProfileStatus;
import com.learning.employemanagementsystem.exception.AlreadyFoundException;
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

    @Override
    public AddEmployeeDto add(AddEmployeeDto employeeDto) {
        if (employeeRepository.existsByEmail(employeeDto.getEmail())) {
            throw new AlreadyFoundException("Email already exists: " + employeeDto.getEmail());
        }
        var period = Period.between(employeeDto.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now());
        employeeDto.setAge(period.getYears());
        var employee = employeeMapper.addEmployeeDtoToEmployee(employeeDto);
        employee.setPassword(passwordEncoder.encode(generateRandomPassword()));
        employee.setIsManager(Boolean.FALSE);
        var updatedEmployee = employeeRepository.save(employee);
        var profile = new Profile();
        profile.setProfileStatus(ProfileStatus.PENDING);
        profile.setJobTitle(JobTitleType.ENGINEER_TRAINEE);
        profile.setEmployee(updatedEmployee);
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

                if ("add".equalsIgnoreCase(action)) {
                    employee.setIsManager(Boolean.TRUE);
                } else if ("remove".equalsIgnoreCase(action)) {
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
            String employeeEmail = rowData.get(0), managerEmail = rowData.get(1), action = rowData.get(2);
            if (StringUtils.isNotBlank(employeeEmail) && StringUtils.isNotBlank(managerEmail) && StringUtils.isNotBlank(action)) {

                if (employeeRepository.existsByEmail(employeeEmail) && employeeRepository.existsByEmail(managerEmail)) {
                    Employee employee = employeeRepository.getByEmail(employeeEmail);
                    Employee manager = employeeRepository.getByEmail(managerEmail);
                    if ("add".equalsIgnoreCase(action)) {
                        employee.setManager(manager);
                        manager.setIsManager(Boolean.TRUE);
                        employeeRepository.save(employee);
                        employeeRepository.save(manager);
                    } else if ("remove".equalsIgnoreCase(action)) {
                        if (manager.getUuid().equals(employee.getManager().getUuid())) {
                            employee.setManager(null);
                            employeeRepository.save(employee);
                        }
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

    public List<List<String>> fileProcess(MultipartFile file) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new RuntimeException("Sheet Not Found");
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
                List<String> rowValue = new ArrayList();

                Iterator<Cell> dataCellIterator = dataRow.cellIterator();
                while (dataCellIterator.hasNext()) {
                    Cell cell = dataCellIterator.next();
                    String cellValue = dataFormatter.formatCellValue(cell);
                    if (StringUtils.isNotBlank(cellValue)) {
                        rowValue.add(cellValue);
                    } else {
                        rowValue.add("");
                    }
                    System.out.println(cellValue + "\t");
                }
                rowValues.add(rowValue);
                System.out.println();
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
