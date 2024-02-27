package com.learning.employemanagementsystem.repository;

import com.learning.employemanagementsystem.entity.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, UUID> {

    List<Skills> getSkillsByEmployee_Uuid(UUID employeeUuid);

    List<Skills> getSkillsByNameAndEmployee_Uuid(String name, UUID employeeUuid);
}
