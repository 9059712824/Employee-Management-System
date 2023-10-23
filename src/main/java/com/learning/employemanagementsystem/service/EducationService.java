package com.learning.employemanagementsystem.service;

import com.learning.employemanagementsystem.dto.EducationDto;
import com.learning.employemanagementsystem.model.EducationModel;

import java.util.List;
import java.util.UUID;

public interface EducationService {
    EducationModel save(EducationDto educationDto, UUID uuid);

    EducationModel update(EducationDto educationDto, UUID uuid);

    EducationModel get(UUID uuid);

    List<EducationModel> getAll(UUID employeeUuid);

    void deleteById(UUID uuid);
}
