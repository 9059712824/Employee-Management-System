package com.learning.employemanagementsystem.dto;

import com.learning.employemanagementsystem.entity.EducationDegree;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationDto {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EducationDegree degree;

    @Column(nullable = false)
    private String schoolName;

    @Column(nullable = false)
    private Double grade;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;
}
