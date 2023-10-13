package com.learning.employemanagementsystem.dao;


import com.learning.employemanagementsystem.dto.AddEmployeeDto;
import com.learning.employemanagementsystem.model.EmployeeModel;

import java.util.List;
import java.util.UUID;

public interface EmployeeDao {
    Boolean existsByEmail(String email);

    EmployeeModel save(EmployeeModel employeeModel);

    EmployeeModel getById(UUID id);

    List<EmployeeModel> getAll();

    EmployeeModel getEmployeeById(UUID id);
}
