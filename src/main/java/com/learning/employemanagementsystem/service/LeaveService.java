package com.learning.employemanagementsystem.service;

import com.learning.employemanagementsystem.dto.*;
import com.learning.employemanagementsystem.entity.Leave;
import com.learning.employemanagementsystem.entity.LeaveStatus;

import java.util.List;
import java.util.UUID;

public interface LeaveService {
    List<ParseLeaveResponseDto> parseLeave(ParseLeaveRequestDto parseLeaveDto);

    List<Leave> applyLeave(UUID employeeId, List<ApplyLeaveDto> applyLeaveDto);

    ViewEmployeeLeavesDto getLeavesByEmployeeId(UUID employeeId);

    List<ViewEmployeeLeavesDto> getAllEmployeesLeavesByManager(UUID managerUuid);

    SuccessResponseDto updateLeavesByManager(UUID managerUuid, LeaveStatus status, List<UpdateLeaveByManagerDto> leavesDto);
}
