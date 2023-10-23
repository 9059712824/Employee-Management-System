package com.learning.employemanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payroll {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "employee_uuid")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "profile_uuid")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "salaryPaid_uuid")
    private SalaryPaid salaryPaid;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false, updatable = false)
    private Double totalAmount;
}
