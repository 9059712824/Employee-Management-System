package com.learning.employemanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "employee_uuid")
    @JsonBackReference
    private Employee employee;

    @Column(name = "work_mode", nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkMode workMode;

    @Column(name = "shift_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttendanceShiftType shiftType;

    @Column(name = "attendance_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttendanceType attendanceType;

    @Column(name = "attendance_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    @Temporal(TemporalType.DATE)
    @Column(name = "attendance_date", nullable = false)
    private Date attendanceDate;

    @CreationTimestamp
    @Column(name = "creation_time", nullable = false, updatable = false)
    private Date creationTime;

    @UpdateTimestamp
    @Column(name = "updated_time", nullable = false)
    private Date updatedTime;
}
