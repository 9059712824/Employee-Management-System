package com.learning.employemanagementsystem.service.impl;

import com.learning.employemanagementsystem.dto.*;
import com.learning.employemanagementsystem.entity.*;
import com.learning.employemanagementsystem.exception.AlreadyFoundException;
import com.learning.employemanagementsystem.exception.NotFoundException;
import com.learning.employemanagementsystem.mapper.LeaveMapper;
import com.learning.employemanagementsystem.repository.EmployeeRepository;
import com.learning.employemanagementsystem.repository.LeaveRepository;
import com.learning.employemanagementsystem.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;

    private final LeaveMapper leaveMapper;

    private final EmployeeRepository employeeRepository;

    private static final String DATE_FORMAT = "yyyy-MM-dd";

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
                .map(ApplyLeaveDto::getDate)
                .filter(leaveDate -> appliedLeaves.stream()
                        .map(Leave::getDate)
                        .anyMatch(appliedLeaveDate -> leaveDate.compareTo(appliedLeaveDate) == 1))
                .findFirst()
                .ifPresent(date -> {
                    throw new AlreadyFoundException("Leave already applied for the date " + new SimpleDateFormat(DATE_FORMAT).format(date));
                });
        var leaves = leaveMapper.applyLeaveDtoToLeaveDay(applyLeaveDto);

        setLeaveDayStatus(leaves, LeaveStatus.WAITING_FOR_APPROVAL);
        leaves.forEach(leave -> leave.setEmployee(employee));

        return leaveRepository.saveAll(leaves);
    }

    @Override
    public ViewEmployeeLeavesDto getLeavesByEmployeeId(UUID employeeId) {

        var employee = isEmployeeExists(employeeId);

        var leaves = leaveRepository.getAllByEmployeeUuid(employeeId);

        Map<LeaveStatus, List<Leave>> leaveStatusMap = new EnumMap<>(LeaveStatus.class);

        Map<Boolean, List<Leave>> dateLeavesmap = leaves.stream()
                .collect(Collectors.partitioningBy(leave -> leave.getDate().compareTo(new Date()) > 0));

        for (LeaveStatus status : LeaveStatus.values()) {
            leaveStatusMap.put(status, filterLeavesByStatus(leaves, status));
        }

        return ViewEmployeeLeavesDto.builder()
                .employeeUuid(employeeId)
                .employeeFirstName(employee.getFirstName())
                .employeeLastName(employee.getLastName())
                .pastLeaves(dateLeavesmap.get(Boolean.FALSE))
                .futureLeaves(dateLeavesmap.get(Boolean.TRUE))
                .approvedLeaves(leaveStatusMap.get(LeaveStatus.APPROVED))
                .waitingForApprovalLeaves(leaveStatusMap.get(LeaveStatus.WAITING_FOR_APPROVAL))
                .declinedLeaves(leaveStatusMap.get(LeaveStatus.DECLINED))
                .cancelledLeaves(leaveStatusMap.get(LeaveStatus.CANCELLED))
                .build();
    }

    @Override
    public List<ViewEmployeeLeavesDto> getAllEmployeesLeavesByManager(UUID managerUuid) {
        isManager(managerUuid);
        return employeeRepository.getAllByManagerUuid(managerUuid)
                .stream()
                .map(employee -> getLeavesByEmployeeId(employee.getUuid()))
                .toList();
    }

    @Override
    public SuccessResponseDto updateLeavesByManager(UUID managerUuid, LeaveStatus status, List<UpdateLeaveByManagerDto> leavesDto) {
        isManager(managerUuid);

        var leaves = leavesDto.stream()
                .filter(leaveDto -> isLeaveEligibleForManager(leaveDto.getLeaveUuid(), managerUuid))
                .map(leaveDto -> {
                    var leave = getLeaveById(leaveDto.getLeaveUuid());
                    leave.setStatus(status);
                    leave.setComments(leaveDto.getComments());
                    return leave;
                })
                .toList();

        leaveRepository.saveAll(leaves);

        return SuccessResponseDto.builder()
                .success(true)
                .data(status.toString())
                .build();
    }

    private List<Leave> filterLeavesByStatus(List<Leave> leaves, LeaveStatus status) {
        return leaves.stream()
                .filter(leave -> leave.getStatus().equals(status))
                .toList();
    }

    private void setLeaveDayStatus(List<Leave> leaves, LeaveStatus status) {
        leaves.forEach(leaveDay -> leaveDay.setStatus(status));
    }

    private Employee isEmployeeExists(UUID employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Didn't found employee with Id: " + employeeId));
    }

    private void isManager(UUID uuid) {
        var manager = isEmployeeExists(uuid);
        if (manager.getIsManager().equals(Boolean.FALSE)) {
            throw new NotFoundException("User is not a Manager");
        }
    }

    private Leave getLeaveById(UUID leaveUuid) {
        return leaveRepository.findById(leaveUuid)
                .orElseThrow(() -> new NotFoundException("leave not found with id : " + leaveUuid));
    }

    private Boolean isLeaveEligibleForManager(UUID leaveUuid, UUID managerUuid) {
        var leave = getLeaveById(leaveUuid);
        if (leave.getEmployee().getManager() == null) {
            throw new NotFoundException("Manager Not found for this colleague to approve leave");
        } else if (Boolean.FALSE.equals(leave.getEmployee().getManager().getUuid().equals(managerUuid))) {
            throw new NotFoundException("Invalid manager trying to approve the request");
        }
        return Boolean.TRUE;
    }
}
