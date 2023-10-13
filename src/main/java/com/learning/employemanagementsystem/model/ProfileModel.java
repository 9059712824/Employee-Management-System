package com.learning.employemanagementsystem.model;

import com.learning.employemanagementsystem.entity.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileModel {
    private UUID id;

    @Enumerated(EnumType.STRING)
    private JobTitleType jobTitle;

    @Enumerated(EnumType.STRING)
    private ProfileStatus profileStatus;

    private Date updatedTime;

    private Employee employee;

    private Department department;

    private SalaryStructure salaryStructure;

    private List<Payroll> payroll;
}
