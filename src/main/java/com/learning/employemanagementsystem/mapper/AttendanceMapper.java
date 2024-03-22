package com.learning.employemanagementsystem.mapper;

import com.learning.employemanagementsystem.dto.ApplyAttendanceDto;
import com.learning.employemanagementsystem.dto.UpdateAttendanceDto;
import com.learning.employemanagementsystem.entity.Attendance;
import org.mapstruct.Mapper;

@Mapper
public interface AttendanceMapper {
    Attendance applyAttendanceDtoToAttendance(ApplyAttendanceDto attendance);

    Attendance updateAttendanceDtoToAttendance(UpdateAttendanceDto attendanceDto);
}
