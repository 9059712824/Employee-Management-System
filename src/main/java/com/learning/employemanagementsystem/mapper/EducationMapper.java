package com.learning.employemanagementsystem.mapper;

import com.learning.employemanagementsystem.dto.EducationDto;
import com.learning.employemanagementsystem.entity.Education;
import com.learning.employemanagementsystem.model.EducationModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface EducationMapper {
    EducationModel addEducationDtoToEducationModel(EducationDto educationDto);

    Education educationModelToEducation(EducationModel educationModel);

    EducationModel educationToEducationModel(Education education);

    List<EducationModel> educationListToEducationModelList(List<Education> educations);
}
