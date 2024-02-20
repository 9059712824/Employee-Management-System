package com.learning.employemanagementsystem.mapper;

import com.learning.employemanagementsystem.dto.ApplyLeaveDto;
import com.learning.employemanagementsystem.entity.Leave;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface LeaveMapper {
    List<Leave> applyLeaveDtoToLeaveDay(List<ApplyLeaveDto> leaveDto);
}
