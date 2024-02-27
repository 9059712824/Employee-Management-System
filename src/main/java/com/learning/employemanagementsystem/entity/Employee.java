package com.learning.employemanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Employee {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    @Length(min = 2, max = 15)
    private String firstName;

    @Column(nullable = false)
    @Length(min = 2, max = 15)
    private String lastName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    @Pattern(regexp = "^\\+?[0-9()-]+")
    @JsonIgnore
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @ManyToOne
    @JoinColumn(name = "manager_uuid")
    private Employee manager;

    @OneToMany(mappedBy = "manager")
    @JsonIgnore
    private List<Employee> employees;

    @Column(nullable = false)
    private Boolean isManager;

    @Column(nullable = false)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).*$",
            message = "Password must contain at least one digit, one special character, and one letter"
    )
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date joiningDate;

    @Temporal(TemporalType.DATE)
    private Date leavingDate;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdTime;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updatedTime;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("employee")
    @JsonIgnore
    private List<Leave> leave;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("employee")
    @JsonIgnore
    private List<Experience> experience;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("employee")
    @JsonIgnore
    private List<Education> education;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("employee")
    private Profile profile;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("employee")
    private OtpAuth otpAuth;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("employee")
    private List<Payroll> payroll;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("employee")
    private List<Skills> skills;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + uuid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                ", age=" + age +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", joiningDate=" + joiningDate +
                ", leavingDate=" + leavingDate +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime;
    }
}