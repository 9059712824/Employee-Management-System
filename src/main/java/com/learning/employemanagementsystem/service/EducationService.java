package com.learning.employemanagementsystem.service;

import com.learning.employemanagementsystem.dto.EducationDto;
import com.learning.employemanagementsystem.entity.Education;

import java.util.List;
import java.util.UUID;

public interface EducationService {
    Education save(EducationDto educationDto, UUID uuid);

    Education update(EducationDto educationDto, UUID uuid);

    Education getById(UUID uuid);

    List<Education> getAll(UUID employeeUuid);

    void deleteById(UUID uuid);
}
