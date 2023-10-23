package com.learning.employemanagementsystem.dao;

import com.learning.employemanagementsystem.model.EducationModel;

import java.util.List;
import java.util.UUID;

public interface EducationDao {
    EducationModel save(EducationModel educationModel);

    List<EducationModel> getAllByEmployeeId(UUID id);

    EducationModel getByUuid(UUID id);

    void deleteById(UUID id);

    Boolean existsById(UUID id);
}
