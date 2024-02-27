package com.learning.employemanagementsystem.service;

import com.learning.employemanagementsystem.dto.AddDepartmentDto;
import com.learning.employemanagementsystem.entity.Department;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface DepartmentService {
    Department add(AddDepartmentDto department);

    void departmentPermission(MultipartFile file) throws IOException;

    Department update(UUID departmentUuid, AddDepartmentDto department);

    void delete(UUID departmentUuid);
}
