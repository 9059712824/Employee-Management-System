package com.learning.employemanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SalaryPaid {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "salaryStructure_uuid")
    private SalaryStructure salaryStructure;

    @Column(nullable = false, updatable = false)
    private Double basicAmount;

    @Column(updatable = false)
    private Double quarterlyBonus;

    @Column(updatable = false)
    private Double monthlyBonus;

    @Column(nullable = false, updatable = false)
    private Double totalAmount;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdTime;

    @OneToMany(mappedBy = "salaryPaid", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("salaryPaid")
    private List<Payroll> payroll;
}
