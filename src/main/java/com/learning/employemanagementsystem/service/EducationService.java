package com.learning.employemanagementsystem.service;

import com.learning.employemanagementsystem.dto.EducationDto;
import com.learning.employemanagementsystem.model.EducationModel;

import java.util.List;
import java.util.UUID;

public interface EducationService {
    EducationModel save(EducationDto educationDto, UUID id);

    EducationModel update(EducationDto educationDto, UUID id);

    EducationModel get(UUID id);

    List<EducationModel> getAll(UUID employeeId);

    void deleteById(UUID id);
}
