package com.learning.employemanagementsystem.service.impl;

import com.learning.employemanagementsystem.dto.ApplyAttendanceDto;
import com.learning.employemanagementsystem.dto.UpdateAttendanceDto;
import com.learning.employemanagementsystem.dto.ViewEmployeeAttendanceDto;
import com.learning.employemanagementsystem.entity.Attendance;
import com.learning.employemanagementsystem.entity.AttendanceStatus;
import com.learning.employemanagementsystem.exception.AlreadyFoundException;
import com.learning.employemanagementsystem.exception.NotFoundException;
import com.learning.employemanagementsystem.mapper.AttendanceMapper;
import com.learning.employemanagementsystem.repository.AttendanceRepository;
import com.learning.employemanagementsystem.service.AttendanceService;
import com.learning.employemanagementsystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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
                .map(date -> new SimpleDateFormat(DATE_FORMAT).format(date))
                .forEach(formattedDate -> {
                    if (appliedAttendances.stream()
                            .map(Attendance::getAttendanceDate)
                            .map(Object::toString)
                            .anyMatch(formattedDate::equals)) {
                        throw new AlreadyFoundException("Attendance already applied for date " + formattedDate);
                    }
                });

        var attendances = attendanceDto.stream()
                .map(attendanceMapper::applyAttendanceDtoToAttendance)
                .toList();

        setAttendanceStatus(attendances, AttendanceStatus.SUBMITTED);
        attendances.forEach(attendance -> attendance.setEmployee(employee));
        return attendanceRepository.saveAll(attendances);
    }

    @Override
    public Attendance update(UUID employeeUuid, UUID attendanceUuid, UpdateAttendanceDto attendanceDto) {
        var attendance = getByUuid(employeeUuid, attendanceUuid);
        employeeService.getById(employeeUuid);

        if (attendance.getUuid().equals(attendanceDto.getUuid()) || attendance.getEmployee().getManager().getUuid().equals(employeeUuid)) {
            attendance.setWorkMode(attendanceDto.getWorkMode());
            attendance.setAttendanceType(attendanceDto.getAttendanceType());
            attendance.setShiftType(attendanceDto.getShiftType());
            attendance.setAttendanceStatus(attendanceDto.getAttendanceStatus());
        }

        return attendanceRepository.save(attendance);
    }

    @Override
    public Attendance getByUuid(UUID employeeUuid, UUID attendanceUuid) {
        var employee = employeeService.getById(employeeUuid);
        return attendanceRepository.findById(attendanceUuid).filter(attendance -> attendance.getEmployee().getUuid().equals(employee.getUuid()))
                .orElseThrow(() ->
                        new NotFoundException("Attendance not found with uuid " + attendanceUuid));
    }

    @Override
    public ViewEmployeeAttendanceDto getEmployeeAttendance(UUID employeeUuid) {
        var employee = employeeService.getById(employeeUuid);

        var attendances = attendanceRepository.getAttendanceByEmployee_Uuid(employeeUuid);

        Map<AttendanceStatus, List<Attendance>> attendanceStatusListMap = new EnumMap<>(AttendanceStatus.class);

        for (AttendanceStatus status : AttendanceStatus.values()) {
            attendanceStatusListMap.put(status, filterAttendanceByStatus(attendances, status));
        }

        return ViewEmployeeAttendanceDto.builder()
                .employeeUuid(employee.getUuid())
                .employeeFirstName(employee.getFirstName())
                .employeeLastName(employee.getLastName())
                .submittedAttendance(attendanceStatusListMap.get(AttendanceStatus.SUBMITTED))
                .waitingForCancellationAttendance(attendanceStatusListMap.get(AttendanceStatus.WAITING_FOR_CANCELLATION))
                .cancelledAttendance(attendanceStatusListMap.get(AttendanceStatus.CANCELLED))
                .build();
    }

    private List<Attendance> filterAttendanceByStatus(List<Attendance> attendances, AttendanceStatus status) {
        return attendances.stream()
                .filter(attendance -> attendance.getAttendanceStatus().equals(status))
                .toList();
    }


    private void setAttendanceStatus(List<Attendance> attendances, AttendanceStatus status) {
        attendances.forEach(attendance -> attendance.setAttendanceStatus(status));
    }
}
