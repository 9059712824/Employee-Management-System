package com.learning.employemanagementsystem.service;

import com.learning.employemanagementsystem.dto.AddEmployeeDto;
import com.learning.employemanagementsystem.dto.AddEmployeeResponseDto;
import com.learning.employemanagementsystem.dto.UpdateLeavingDate;
import com.learning.employemanagementsystem.model.EmployeeModel;

import java.util.UUID;

public interface EmployeeService {
    AddEmployeeDto addEmployee(AddEmployeeDto employeeDto);

    EmployeeModel viewEmployee(UUID id);

    void updateLeavingDate(UUID id, UpdateLeavingDate updateLeavingDate);
}
