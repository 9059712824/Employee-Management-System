package com.learning.employemanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SalaryStructure {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    private Double salary;

    @Column(nullable = false)
    private Double basicSalary;

    @Column
    private Double quarterlyBonus;

    @Column
    private Double monthlyBonus;

    @OneToOne
    @JoinColumn(name = "profile_uuid")
    private Profile profile;

    @OneToMany(mappedBy = "salaryStructure", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SalaryPaid> salaryPaid;
}
