package com.learning.employemanagementsystem.mapper;

import com.learning.employemanagementsystem.dto.EducationDto;
import com.learning.employemanagementsystem.entity.Education;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface EducationMapper {
    Education addEducationDtoToEducation(EducationDto educationDto);
}
