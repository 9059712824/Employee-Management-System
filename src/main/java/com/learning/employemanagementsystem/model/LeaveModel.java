package com.learning.employemanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learning.employemanagementsystem.entity.Employee;
import com.learning.employemanagementsystem.entity.LeaveStatus;
import com.learning.employemanagementsystem.entity.LeaveType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveModel {

    private UUID id;

    @JsonIgnore
    private Employee employee;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private LeaveType type;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    private String reason;

    private String comments;

    private Date createdDate;

    private Date updatedTime;
}
