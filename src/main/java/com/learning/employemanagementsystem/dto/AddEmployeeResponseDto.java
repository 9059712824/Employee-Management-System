package com.learning.employemanagementsystem.dto;

import com.learning.employemanagementsystem.entity.Gender;
import com.learning.employemanagementsystem.entity.Profile;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddEmployeeResponseDto {

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Date dateOfBirth;

    private Integer age;

    private String phoneNumber;

    private String email;

    private Date joiningDate;

    private Profile profile;
}
