package com.learning.employemanagementsystem.repository;

import com.learning.employemanagementsystem.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {

    Department getByName(String name);
}
