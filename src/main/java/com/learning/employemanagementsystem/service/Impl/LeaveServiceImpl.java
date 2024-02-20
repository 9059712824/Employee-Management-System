package com.learning.employemanagementsystem.service.Impl;

import com.learning.employemanagementsystem.dto.ApplyLeaveDto;
import com.learning.employemanagementsystem.dto.ParseLeaveRequestDto;
import com.learning.employemanagementsystem.dto.ParseLeaveResponseDto;
import com.learning.employemanagementsystem.dto.ViewEmployeeLeavesDto;
import com.learning.employemanagementsystem.entity.*;
import com.learning.employemanagementsystem.exception.AlreadyFoundException;
import com.learning.employemanagementsystem.exception.NotFoundException;
import com.learning.employemanagementsystem.mapper.LeaveMapper;
import com.learning.employemanagementsystem.repository.EmployeeRepository;
import com.learning.employemanagementsystem.repository.LeaveRepository;
import com.learning.employemanagementsystem.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;

    private final LeaveMapper leaveMapper;

    private final EmployeeRepository employeeRepository;

    @Override
    public List<ParseLeaveResponseDto> parseLeave(ParseLeaveRequestDto parseLeaveDto) {
        var dates = new ArrayList<>();

        var calender = Calendar.getInstance();
        calender.setTime(parseLeaveDto.getStartDate());

        while (!calender.getTime().after(parseLeaveDto.getEndDate())) {
            dates.add(calender.getTime());
            calender.add(Calendar.DATE, 1);
        }

        List<ParseLeaveResponseDto> response = new ArrayList<>();
        for (var date : dates) {
            response.add(ParseLeaveResponseDto.builder()
                    .date((Date) date)
                    .type(LeaveType.FULL_DAY)
                    .build());
        }
        return response;
    }

    @Override
    public List<Leave> applyLeave(UUID employeeId, List<ApplyLeaveDto> applyLeaveDto) {
        var employee = isEmployeeExists(employeeId);
        var appliedLeaves = leaveRepository.getAllByEmployeeUuid(employeeId);

        applyLeaveDto.stream()
                .flatMap(leaveDto -> appliedLeaves.stream()
                        .map(Leave::getDate)
                        .filter(dtoDate -> leaveDto.getDate().compareTo(dtoDate) == 1))
                .findFirst()
                .ifPresent(date -> {
                    throw new AlreadyFoundException("Leave Already applied for the date " +date);
                });
        var leaves = leaveMapper.applyLeaveDtoToLeaveDay(applyLeaveDto);

        setLeaveDayStatus(leaves, LeaveStatus.WAITING_FOR_APPROVAL);
        leaves.forEach(leave -> leave.setEmployee(employee));

        return leaveRepository.saveAll(leaves);
    }

    @Override
    public ViewEmployeeLeavesDto getLeavesByEmployeeId(UUID employeeId) {
        var leaves = leaveRepository.getAllByEmployeeUuid(employeeId);

        if (leaves.isEmpty()) {
            throw new NotFoundException("No Applied Leaves");
        }

        Map<LeaveStatus, List<Leave>> leaveStatusMap = new EnumMap<>(LeaveStatus.class);

        Map<Boolean, List<Leave>> dateLeavesmap = leaves.stream()
                .collect(Collectors.partitioningBy(leave -> leave.getDate().compareTo(new Date()) > 0));

        for (LeaveStatus status : LeaveStatus.values()) {
            leaveStatusMap.put(status, filterLeavesByStatus(leaves, status));
        }

        return ViewEmployeeLeavesDto.builder()
                .employeeUuid(employeeId)
                .pastLeaves(dateLeavesmap.get(Boolean.FALSE))
                .futureLeaves(dateLeavesmap.get(Boolean.TRUE))
                .approvedLeaves(leaveStatusMap.get(LeaveStatus.APPROVED))
                .waitingForApprovalLeaves(leaveStatusMap.get(LeaveStatus.WAITING_FOR_APPROVAL))
                .declinedLeaves(leaveStatusMap.get(LeaveStatus.DECLINED))
                .cancelledLeaves(leaveStatusMap.get(LeaveStatus.CANCELLED))
                .build();
    }

    private List<Leave> filterLeavesByStatus(List<Leave> leaves, LeaveStatus status) {
        return leaves.stream()
                .filter(leave -> leave.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    private void setLeaveDayStatus(List<Leave> leaves, LeaveStatus status) {
        leaves.forEach(leaveDay -> leaveDay.setStatus(status));
    }

    private Employee isEmployeeExists(UUID employeeId) {
        var employee = employeeRepository.findById(employeeId);
        if (employee.isEmpty()) {
            throw new NotFoundException("Didn't found employee with Id: " + employeeId);
        }
        return employee.get();
    }
}
