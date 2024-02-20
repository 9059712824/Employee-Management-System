package com.learning.employemanagementsystem.mapper;

import com.learning.employemanagementsystem.dto.AddDepartmentDto;
import com.learning.employemanagementsystem.entity.Department;
import org.mapstruct.Mapper;

@Mapper
public interface DepartmentMapper {
    Department departmentToDepartment(Department department);

    Department DepartmentToDepartment(Department Department);

    Department addDepartmentDtoToDepartment(AddDepartmentDto department);

}
