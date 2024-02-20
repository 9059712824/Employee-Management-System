package com.learning.employemanagementsystem.mapper;

import com.learning.employemanagementsystem.dto.AddEmployeeDto;
import com.learning.employemanagementsystem.dto.AddEmployeeResponseDto;
import com.learning.employemanagementsystem.entity.Employee;
import com.learning.employemanagementsystem.entity.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    Employee addEmployeeDtoToEmployee(AddEmployeeDto addEmployeeDto);

    AddEmployeeDto EmployeeToAddEmployeeDto(Employee Employee);
}
