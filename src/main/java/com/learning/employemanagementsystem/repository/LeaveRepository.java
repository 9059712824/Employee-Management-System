package com.learning.employemanagementsystem.repository;

import com.learning.employemanagementsystem.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LeaveRepository extends JpaRepository<Leave, UUID> {
}
