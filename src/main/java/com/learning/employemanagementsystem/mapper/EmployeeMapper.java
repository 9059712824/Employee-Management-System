package com.learning.employemanagementsystem.mapper;

import com.learning.employemanagementsystem.dto.AddEmployeeDto;
import com.learning.employemanagementsystem.dto.AddEmployeeResponseDto;
import com.learning.employemanagementsystem.entity.Employee;
import com.learning.employemanagementsystem.model.EmployeeModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    Employee employeeModelToEmployee(EmployeeModel employeeModel);

    EmployeeModel employeeToEmployeeModel(Employee employee);

    List<EmployeeModel> employeeListToEmployeeMOdelList(List<Employee> employees);

    EmployeeModel addEmployeeDtoToEmployeeModel(AddEmployeeDto addEmployeeDto);

    AddEmployeeDto employeeModelToAddEmployeeDto(EmployeeModel employeeModel);
}
