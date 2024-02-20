package com.learning.employemanagementsystem.service;

import com.learning.employemanagementsystem.dto.AddEmployeeDto;
import com.learning.employemanagementsystem.dto.AddEmployeeResponseDto;
import com.learning.employemanagementsystem.dto.UpdateLeavingDate;
import com.learning.employemanagementsystem.entity.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    AddEmployeeDto add(AddEmployeeDto employeeDto);

    Employee getById(UUID id);

    void updateLeavingDate(UUID id, UpdateLeavingDate updateLeavingDate);

    List<Employee> viewAll();

    void managerAccess(MultipartFile file) throws IOException;

    void updateManagerId(MultipartFile file) throws IOException;

    List<Employee> getByManagerUuid(UUID managerId);
}
