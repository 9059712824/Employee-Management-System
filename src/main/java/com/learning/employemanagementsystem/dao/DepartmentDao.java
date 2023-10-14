package com.learning.employemanagementsystem.dao;

import com.learning.employemanagementsystem.dto.AddDepartmentDto;
import com.learning.employemanagementsystem.model.DepartmentModel;
import com.learning.employemanagementsystem.repository.DepartmentRepository;

public interface DepartmentDao {
    DepartmentModel save(DepartmentModel department);

    DepartmentModel getByName(String name);
}
