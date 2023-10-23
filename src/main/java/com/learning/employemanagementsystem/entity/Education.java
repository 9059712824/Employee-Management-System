package com.learning.employemanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Education {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "employee_uuid")
    @JsonIgnore
    private Employee employee;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EducationDegree degree;

    @Column(nullable = false)
    private String schoolName;

    @Column(nullable = false)
    private Double grade;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date endDate;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdTime;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updatedTime;
}
