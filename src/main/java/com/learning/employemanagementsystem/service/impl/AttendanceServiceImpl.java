package com.learning.employemanagementsystem.service.impl;

import com.learning.employemanagementsystem.dto.ApplyAttendanceDto;
import com.learning.employemanagementsystem.dto.UpdateAttendanceDto;
import com.learning.employemanagementsystem.entity.Attendance;
import com.learning.employemanagementsystem.entity.AttendanceStatus;
import com.learning.employemanagementsystem.exception.AlreadyFoundException;
import com.learning.employemanagementsystem.exception.NotFoundException;
import com.learning.employemanagementsystem.mapper.AttendanceMapper;
import com.learning.employemanagementsystem.repository.AttendanceRepository;
import com.learning.employemanagementsystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final EmployeeService employeeService;

    private final AttendanceMapper attendanceMapper;

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public List<Attendance> apply(UUID employeeUuid, List<ApplyAttendanceDto> attendanceDto) {
        var employee = employeeService.getById(employeeUuid);

        var appliedAttendances = attendanceRepository.getAttendanceByEmployee_Uuid(employeeUuid);

        attendanceDto.stream()
                .map(ApplyAttendanceDto::getAttendanceDate)
                .filter(appliedAttendance -> appliedAttendances.stream()
                        .map(Attendance::getAttendanceDate)
                        .anyMatch(latest -> appliedAttendance.compareTo(latest) == 1))
                .findFirst()
                .ifPresent(date -> {
                    throw new AlreadyFoundException("Attendance already applied for date " + new SimpleDateFormat(DATE_FORMAT).format(date));
                });

        var attendances = attendanceDto.stream()
                .map(attendanceMapper::applyAttendanceDtoToAttendance)
                .toList();

        setAttendanceStatus(attendances, AttendanceStatus.SUBMITTED);
        attendances.forEach(attendance -> attendance.setEmployee(employee));
        var savedAttendance = attendanceRepository.saveAll(attendances);

        return savedAttendance;
    }

    @Override
    public Attendance update(UUID employeeUuid, UUID attendanceUuid, UpdateAttendanceDto attendanceDto) {
        var attendance = getByUuid(employeeUuid, attendanceUuid);

        var employee = employeeService.getById(employeeUuid);

        if (attendance.getUuid().equals(attendanceDto.getUuid()) || attendance.getEmployee().getManager().getUuid().equals(employeeUuid)) {
            attendance.setWorkMode(attendanceDto.getWorkMode());
            attendance.setAttendanceType(attendanceDto.getAttendanceType());
            attendance.setShiftType(attendanceDto.getShiftType());
            attendance.setAttendanceStatus(attendanceDto.getAttendanceStatus());
        }
        return null;
    }

    @Override
    public Attendance getByUuid(UUID employeeUuid, UUID attendanceUuid) {
        var employee = employeeService.getById(employeeUuid);
        return attendanceRepository.findById(attendanceUuid).filter(attendance -> attendance.getEmployee().getUuid().equals(employee.getUuid()))
                .orElseThrow(() ->
                        new NotFoundException("Attendance not found with uuid " + attendanceUuid));
    }


    private void setAttendanceStatus(List<Attendance> attendances, AttendanceStatus status) {
        attendances.forEach(attendance -> attendance.setAttendanceStatus(status));
    }
}
