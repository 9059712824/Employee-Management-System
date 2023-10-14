package com.learning.employemanagementsystem.dto;

import com.learning.employemanagementsystem.entity.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddEmployeeDto {

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private Integer age;

    private String phoneNumber;

    private String email;

    @Temporal(TemporalType.DATE)
    private Date joiningDate;
}