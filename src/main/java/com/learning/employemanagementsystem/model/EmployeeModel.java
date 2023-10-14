package com.learning.employemanagementsystem.model;

import com.learning.employemanagementsystem.entity.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeModel {
    private UUID id;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private Integer age;

    @Pattern(regexp = "^\\+?[0-9()-]+")
    private String phoneNumber;

    @Email
    private String email;

    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).*$",
            message = "Password must contain at least one digit, one special character, and one letter"
    )
    private String password;

    @Temporal(TemporalType.DATE)
    private Date joiningDate;

    @Temporal(TemporalType.DATE)
    private Date leavingDate;

    @CreationTimestamp
    private Date createdTime;

    @UpdateTimestamp
    private Date updatedTime;

    private List<Leave> leave;

    private List<Experience> experience;

    private List<Education> education;

    private Profile profile;

    private List<Payroll> payroll;

    private OtpAuth otpAuth;
}