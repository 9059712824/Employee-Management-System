package com.learning.employemanagementsystem.repository;

import com.learning.employemanagementsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Boolean existsByEmail(String email);

    Employee getByEmail(String email);

    List<Employee> getAllByManagerUuid(UUID manageruuid);
}
