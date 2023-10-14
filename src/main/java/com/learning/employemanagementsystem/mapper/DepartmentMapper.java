package com.learning.employemanagementsystem.mapper;

import com.learning.employemanagementsystem.dto.AddDepartmentDto;
import com.learning.employemanagementsystem.entity.Department;
import com.learning.employemanagementsystem.model.DepartmentModel;
import org.apache.commons.math3.analysis.function.Add;
import org.mapstruct.Mapper;

@Mapper
public interface DepartmentMapper {
    DepartmentModel departmentToDepartmentModel(Department department);

    Department departmentModelToDepartment(DepartmentModel departmentModel);

    Department addDepartmentDtoToDepartment(AddDepartmentDto department);

    DepartmentModel addDepartmentToDepartmentModel(AddDepartmentDto departmentDto);
}
