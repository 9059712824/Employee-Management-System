package com.learning.employemanagementsystem.repository;

import com.learning.employemanagementsystem.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, UUID> {
    List<Leave> getAllByEmployeeUuid(UUID employeeUuid);
}
