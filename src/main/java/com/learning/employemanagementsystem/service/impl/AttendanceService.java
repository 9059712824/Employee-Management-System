package com.learning.employemanagementsystem.service.impl;

import com.learning.employemanagementsystem.dto.ApplyAttendanceDto;
import com.learning.employemanagementsystem.dto.UpdateAttendanceDto;
import com.learning.employemanagementsystem.entity.Attendance;

import java.util.List;
import java.util.UUID;

public interface AttendanceService {
    List<Attendance> apply(UUID employeeUuid, List<ApplyAttendanceDto> attendanceDto);

    Attendance update(UUID employeeUuid, UUID attendanceUuid, UpdateAttendanceDto attendanceDto);

    Attendance getByUuid(UUID employeeUuid, UUID attendanceUuid);
}
