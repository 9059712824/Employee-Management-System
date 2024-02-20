package com.learning.employemanagementsystem.service;

import com.learning.employemanagementsystem.dto.ApplyLeaveDto;
import com.learning.employemanagementsystem.dto.ParseLeaveRequestDto;
import com.learning.employemanagementsystem.dto.ParseLeaveResponseDto;
import com.learning.employemanagementsystem.dto.ViewEmployeeLeavesDto;
import com.learning.employemanagementsystem.entity.Leave;

import java.util.List;
import java.util.UUID;

public interface LeaveService {
    List<ParseLeaveResponseDto> parseLeave(ParseLeaveRequestDto parseLeaveDto);

    List<Leave> applyLeave(UUID employeeId, List<ApplyLeaveDto> applyLeaveDto);

    ViewEmployeeLeavesDto getLeavesByEmployeeId(UUID employeeId);
}
