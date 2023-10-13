package com.learning.employemanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Profile {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, name = "job_title")
    @Enumerated(EnumType.STRING)
    private JobTitleType jobTitle;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProfileStatus profileStatus;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updatedTime;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("profile")
    private SalaryStructure salaryStructure;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("profile")
    private List<Payroll> payroll;
}
