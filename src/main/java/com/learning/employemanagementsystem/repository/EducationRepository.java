package com.learning.employemanagementsystem.repository;

import com.learning.employemanagementsystem.entity.Education;
import com.learning.employemanagementsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EducationRepository extends JpaRepository<Education, UUID> {
    List<Education> getAllByEmployee_Id(UUID id);
}
