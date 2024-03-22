package com.learning.employemanagementsystem.dto;

import com.learning.employemanagementsystem.entity.AttendanceShiftType;
import com.learning.employemanagementsystem.entity.AttendanceStatus;
import com.learning.employemanagementsystem.entity.AttendanceType;
import com.learning.employemanagementsystem.entity.WorkMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyAttendanceDto {

    private WorkMode workMode;

    private AttendanceShiftType shiftType;

    private AttendanceType attendanceType;

    private AttendanceStatus attendanceStatus;

    private Date attendanceDate;
}
