package com.learning.employemanagementsystem.controller;

import com.learning.employemanagementsystem.dto.ApplyLeaveDto;
import com.learning.employemanagementsystem.dto.ParseLeaveRequestDto;
import com.learning.employemanagementsystem.service.LeaveService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/leave")
@AllArgsConstructor
@RestController
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping("/applyLeave/{employeeId}")
    public ResponseEntity<?> add(@PathVariable UUID employeeId, @RequestBody List<ApplyLeaveDto> applyLeaveDto) {
        return new ResponseEntity<>(leaveService.applyLeave(employeeId, applyLeaveDto), HttpStatus.CREATED);
    }

    @GetMapping("/get-leaves/{employeeId}")
    public ResponseEntity<?> getLeavesByEmployeeId(@PathVariable UUID employeeId) {
        return new ResponseEntity<>(leaveService.getLeavesByEmployeeId(employeeId), HttpStatus.OK);
    }

    @PostMapping("/parse-leave")
    public ResponseEntity<?> parseLeave(@RequestBody ParseLeaveRequestDto parseLeaveDto) {
        return new ResponseEntity<>(leaveService.parseLeave(parseLeaveDto), HttpStatus.OK);
    }
}
