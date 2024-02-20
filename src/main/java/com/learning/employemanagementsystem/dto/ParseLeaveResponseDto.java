package com.learning.employemanagementsystem.dto;

import com.learning.employemanagementsystem.entity.LeaveType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParseLeaveResponseDto {

    @Temporal(TemporalType.DATE)
    private Date date;

    private LeaveType type;
}
