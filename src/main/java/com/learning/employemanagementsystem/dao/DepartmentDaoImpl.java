package com.learning.employemanagementsystem.dao;

import com.learning.employemanagementsystem.dto.AddDepartmentDto;
import com.learning.employemanagementsystem.mapper.DepartmentMapper;
import com.learning.employemanagementsystem.model.DepartmentModel;
import com.learning.employemanagementsystem.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class DepartmentDaoImpl implements DepartmentDao{

    private final DepartmentMapper departmentMapper;

    private final DepartmentRepository departmentRepository;
    @Override
    public DepartmentModel save(DepartmentModel department) {
        return departmentMapper.departmentToDepartmentModel(departmentRepository.save(departmentMapper.departmentModelToDepartment(department)));
    }

    @Override
    public DepartmentModel getByName(String name) {
        return departmentMapper.departmentToDepartmentModel(departmentRepository.getByName(name));
    }
}
