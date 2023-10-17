package com.learning.employemanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learning.employemanagementsystem.entity.EducationDegree;
import com.learning.employemanagementsystem.entity.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationModel {

    private UUID id;

    @JsonIgnore
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private EducationDegree degree;

    private String schoolName;

    private Double grade;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    private Date createdTime;

    private Date updatedTime;
}
