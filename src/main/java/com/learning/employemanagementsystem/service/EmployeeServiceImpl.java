package com.learning.employemanagementsystem.service;

import com.learning.employemanagementsystem.dao.EmployeeDao;
import com.learning.employemanagementsystem.dao.ProfileDao;
import com.learning.employemanagementsystem.dto.AddEmployeeDto;
import com.learning.employemanagementsystem.dto.AddEmployeeResponseDto;
import com.learning.employemanagementsystem.dto.UpdateLeavingDate;
import com.learning.employemanagementsystem.entity.JobTitleType;
import com.learning.employemanagementsystem.entity.ProfileStatus;
import com.learning.employemanagementsystem.exception.AlreadyFoundException;
import com.learning.employemanagementsystem.exception.NotFoundException;
import com.learning.employemanagementsystem.mapper.EmployeeMapper;
import com.learning.employemanagementsystem.mapper.ProfileMapper;
import com.learning.employemanagementsystem.model.EmployeeModel;
import com.learning.employemanagementsystem.model.ProfileModel;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.functors.WhileClosure;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;

    private final ProfileDao profileDao;

    private final EmployeeMapper employeeMapper;

    private final ProfileMapper profileMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AddEmployeeDto add(AddEmployeeDto employeeDto) {
        if (employeeDao.existsByEmail(employeeDto.getEmail())) {
            throw new AlreadyFoundException("Email already exists: " + employeeDto.getEmail());
        }
        Period period = Period.between(employeeDto.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now());
        employeeDto.setAge(period.getYears());
        var employee = employeeMapper.addEmployeeDtoToEmployeeModel(employeeDto);
        employee.setPassword(passwordEncoder.encode(generateRandomPassword()));
        var updatedEmployee = employeeDao.save(employee);
        var profile = new ProfileModel();
        profile.setProfileStatus(ProfileStatus.PENDING);
        profile.setJobTitle(JobTitleType.ENGINEER_TRAINEE);
        profile.setEmployee(employeeMapper.employeeModelToEmployee(updatedEmployee));
        employee.setProfile(profileMapper.profileModelToProfile(profile));

        profileDao.save(profile);
        return employeeMapper.employeeModelToAddEmployeeDto(employee);
    }

    @Override
    public EmployeeModel view(UUID id) {
        return employeeDao.getEmployeeById(id);
    }

    @Override
    public void updateLeavingDate(UUID id, UpdateLeavingDate updateLeavingDate) {
        EmployeeModel employee = employeeDao.getEmployeeById(id);
        if (employee == null) {
            throw new NotFoundException("employee not found with id: " + id);
        }
        employee.setLeavingDate(updateLeavingDate.getLeavingDate());
        employeeDao.save(employee);
    }

    @Override
    public List<EmployeeModel> viewAll() {
        var employees = employeeDao.getAll();
        return employees;
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
                EmployeeModel employee = employeeDao.getByEmail(email);

                if ("add".equalsIgnoreCase(action)) {
                    employee.setIsManager(Boolean.TRUE);
                } else if ("remove".equalsIgnoreCase(action)) {
                    employee.setIsManager(Boolean.FALSE);
                }

                employeeDao.save(employee);
            }
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
        String password = null;
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
