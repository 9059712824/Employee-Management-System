package com.learning.employemanagementsystem.service.impl;

import com.learning.employemanagementsystem.dto.AddDepartmentDto;
import com.learning.employemanagementsystem.entity.Department;
import com.learning.employemanagementsystem.exception.NotFoundException;
import com.learning.employemanagementsystem.mapper.DepartmentMapper;
import com.learning.employemanagementsystem.entity.Employee;
import com.learning.employemanagementsystem.repository.DepartmentRepository;
import com.learning.employemanagementsystem.repository.EmployeeRepository;
import com.learning.employemanagementsystem.repository.ProfileRepository;
import com.learning.employemanagementsystem.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final EmployeeRepository employeeRepository;

    private final ProfileRepository profileRepository;

    private final DepartmentMapper departmentMapper;

    @Override
    public Department add(AddDepartmentDto department) {
        return departmentRepository.save(departmentMapper.addDepartmentDtoToDepartment(department));
    }

    public void departmentPermission(MultipartFile file) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

        Sheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            throw new NotFoundException("Sheet Not Found");
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

            Iterator<Cell> dataCellIterator = dataRow.cellIterator();
            List<String> rowValue = new ArrayList<>();
            while (dataCellIterator.hasNext()) {
                Cell cell = dataCellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                rowValue.add(cellValue);
            }
            rowValues.add(rowValue);
        }

        for (List<String> value : rowValues) {
            var department = departmentRepository.getByName(value.get(0).trim());
            var employee = employeeRepository.getByEmail(value.get(1));
            var action = value.get(2);
            if (employee != null && department != null) {
                if (action.equalsIgnoreCase("add")) {
                    var profile = employee.getProfile();
                    profile.setDepartment(departmentMapper.DepartmentToDepartment(department));
                    profileRepository.save(profile);
                } else if (action.equalsIgnoreCase("remove")) {
                    var profile = employee.getProfile();
                    if (profile.getDepartment().getUuid().equals(department.getUuid())) {
                        profile.setDepartment(null);
                        profileRepository.save(profile);
                    }
                }
            }
        }
    }

    @Override
    public Department update(UUID departmentUuid, AddDepartmentDto departmentDto) {
        var department = isDepartmentExists(departmentUuid);
        department.setName(departmentDto.getName());
        return departmentRepository.save(department);
    }

    @Override
    public void delete(UUID departmentUuid) {
        isDepartmentExists(departmentUuid);
        departmentRepository.deleteById(departmentUuid);
    }

    private Department isDepartmentExists(UUID departmentUuid) {
        var department = departmentRepository.findById(departmentUuid);
        if (department.isEmpty()) {
            throw new NotFoundException("Department not exists with Id: " + departmentUuid);
        }
        return department.get();
    }

}
