package com.learning.employemanagementsystem.dto;

import com.learning.employemanagementsystem.entity.LeaveStatus;
import com.learning.employemanagementsystem.entity.LeaveType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplyLeaveDto {

    @Temporal(TemporalType.DATE)
    private Date date;

    private LeaveType type;

    private String reason;
}
