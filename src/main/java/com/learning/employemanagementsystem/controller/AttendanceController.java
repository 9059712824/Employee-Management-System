package com.learning.employemanagementsystem.controller;

import com.learning.employemanagementsystem.dto.ApplyAttendanceDto;
import com.learning.employemanagementsystem.dto.UpdateAttendanceDto;
import com.learning.employemanagementsystem.service.impl.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/attendance")
@RestController
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/apply/{employeeUuid}")
    public ResponseEntity<?> apply(@PathVariable UUID employeeUuid, @RequestBody List<ApplyAttendanceDto> attendanceDto) {
        return new ResponseEntity<>(attendanceService.apply(employeeUuid, attendanceDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{employeeUuid}/attendance/{attendanceUuid}")
    public ResponseEntity<?> update(@PathVariable UUID employeeUuid, @PathVariable UUID attendanceUuid, @RequestBody UpdateAttendanceDto attendanceDto) {
        return new ResponseEntity<>(attendanceService.update(employeeUuid, attendanceUuid, attendanceDto), HttpStatus.ACCEPTED);
    }

    @GetMapping("/get/{employeeUuid}/attendance/{attendanceUuid}")
    public ResponseEntity<?> get(@PathVariable UUID employeeUuid, @PathVariable UUID attendanceUuid) {
        return new ResponseEntity<>(attendanceService.getByUuid(employeeUuid, attendanceUuid), HttpStatus.OK);
    }
}
