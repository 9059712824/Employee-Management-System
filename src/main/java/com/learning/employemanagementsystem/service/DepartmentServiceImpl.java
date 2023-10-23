package com.learning.employemanagementsystem.service;

import com.learning.employemanagementsystem.dao.DepartmentDao;
import com.learning.employemanagementsystem.dao.EmployeeDao;
import com.learning.employemanagementsystem.dao.ProfileDao;
import com.learning.employemanagementsystem.dto.AddDepartmentDto;
import com.learning.employemanagementsystem.mapper.DepartmentMapper;
import com.learning.employemanagementsystem.mapper.ProfileMapper;
import com.learning.employemanagementsystem.model.DepartmentModel;
import com.learning.employemanagementsystem.model.EmployeeModel;
import com.learning.employemanagementsystem.model.ProfileModel;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentDao departmentDao;

    private final EmployeeDao employeeDao;

    private final ProfileDao profileDao;

    private final ProfileMapper profileMapper;

    private final DepartmentMapper departmentMapper;

    @Override
    public DepartmentModel add(AddDepartmentDto department) {
        return departmentDao.save(departmentMapper.addDepartmentToDepartmentModel(department));
    }

    public void departmentPermission(MultipartFile file) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

        Sheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            throw new RuntimeException("Sheet Not Found");
        }

        // Read and store the column headings (header)
        Row headerRow = sheet.getRow(0);
        List<String> columnHeadings = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();

        Iterator<Cell> headerCellIterator = headerRow.cellIterator();
        while (headerCellIterator.hasNext()) {
            Cell cell = headerCellIterator.next();
            String cellValue = dataFormatter.formatCellValue(cell);
            columnHeadings.add(cellValue);
        }

        // Read and print the data
        Iterator<Row> rowIterator = sheet.iterator();
        // Skip the header row (already processed)
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
                System.out.print(cellValue + "\t");
            }
            rowValues.add(rowValue);
            System.out.println();
        }

        for (List<String> value : rowValues) {
            DepartmentModel department = departmentDao.getByName(value.get(0));
            EmployeeModel employee = employeeDao.getByEmail(value.get(1));
            String action = value.get(2);
            if (employee != null && department != null) {
                if (action.equalsIgnoreCase("add")) {
                    ProfileModel profile = profileMapper.profileToProfileModel(employee.getProfile());
                    profile.setDepartment(departmentMapper.departmentModelToDepartment(department));
                    profileDao.save(profile);
                } else if (action.equalsIgnoreCase("remove")) {
                    ProfileModel profile = profileMapper.profileToProfileModel(employee.getProfile());
                    if (profile.getDepartment().getUuid().equals(department.getId())) {
                        profile.setDepartment(null);
                        profileDao.save(profile);
                    }
                }
            }
        }
    }

}
