package com.learning.employemanagementsystem.controller;

import com.learning.employemanagementsystem.dto.ApplyLeaveDto;
import com.learning.employemanagementsystem.dto.ParseLeaveRequestDto;
import com.learning.employemanagementsystem.dto.UpdateLeaveByManagerDto;
import com.learning.employemanagementsystem.entity.LeaveStatus;
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

    @GetMapping("/get-leaves/manager/{managerUuid}")
    public ResponseEntity<?> getAllEmployeesLeavesByManager(@PathVariable UUID managerUuid) {
        return new ResponseEntity<>(leaveService.getAllEmployeesLeavesByManager(managerUuid), HttpStatus.OK);
    }

    @PostMapping("/update-leaves/manager/{managerUuid}")
    public ResponseEntity<?> updateLeavesByManager(@PathVariable UUID managerUuid,
                                                   @RequestParam LeaveStatus status,
                                                   @RequestBody List<UpdateLeaveByManagerDto> leavesDto) {
        return new ResponseEntity<>(leaveService.updateLeavesByManager(managerUuid, status, leavesDto), HttpStatus.ACCEPTED);
    }
}
