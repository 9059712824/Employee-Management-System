package com.learning.employemanagementsystem.repository;

import com.learning.employemanagementsystem.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
    List<Attendance> getAttendanceByEmployee_Uuid(UUID employeeUuid);
}
