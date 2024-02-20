package com.learning.employemanagementsystem.service;

import com.learning.employemanagementsystem.dto.AddDepartmentDto;
import com.learning.employemanagementsystem.entity.Department;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DepartmentService {
    Department add(AddDepartmentDto department);

    void departmentPermission(MultipartFile file) throws IOException;
}
