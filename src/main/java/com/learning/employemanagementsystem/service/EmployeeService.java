package com.learning.employemanagementsystem.service;

import com.learning.employemanagementsystem.dto.AddEmployeeDto;
import com.learning.employemanagementsystem.dto.AddEmployeeResponseDto;
import com.learning.employemanagementsystem.dto.UpdateLeavingDate;
import com.learning.employemanagementsystem.model.EmployeeModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    AddEmployeeDto add(AddEmployeeDto employeeDto);

    EmployeeModel view(UUID id);

    void updateLeavingDate(UUID id, UpdateLeavingDate updateLeavingDate);

    List<EmployeeModel> viewAll();

    void managerAccess(MultipartFile file) throws IOException;

    void updateManagerId(MultipartFile file) throws IOException;
}
