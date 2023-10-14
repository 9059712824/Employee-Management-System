package com.learning.employemanagementsystem.model;

import com.learning.employemanagementsystem.entity.Employee;
import com.learning.employemanagementsystem.entity.LeaveStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveModel {

    private UUID id;

    private Employee employee;

    @Temporal(TemporalType.DATE)
    private Date Date;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    private String reason;

    private String comments;

    private Date createdDate;

    private Date updatedTime;
}
