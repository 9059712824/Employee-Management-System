package com.learning.employemanagementsystem.service.impl;

import com.learning.employemanagementsystem.dto.EducationDto;
import com.learning.employemanagementsystem.entity.EducationDegree;
import com.learning.employemanagementsystem.exception.AlreadyFoundException;
import com.learning.employemanagementsystem.exception.NotFoundException;
import com.learning.employemanagementsystem.mapper.EducationMapper;
import com.learning.employemanagementsystem.entity.Education;
import com.learning.employemanagementsystem.repository.EducationRepository;
import com.learning.employemanagementsystem.service.EducationService;
import com.learning.employemanagementsystem.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;

    private final EducationMapper educationMapper;

    private final EmployeeService employeeService;

    @Override
    public Education save(EducationDto educationDto, UUID id) {
        var employee = employeeService.getById(id);

        var educations = educationRepository.getAllByEmployee_Uuid(id);
        var degree = educationDto.getDegree();

        if (educations == null) {
            if (degree != EducationDegree.SSC_10TH) {
                throw new NotFoundException("Education Details of " + EducationDegree.SSC_10TH + " not found");
            }
        } else {
            var isDegreePresent = educations.stream()
                    .filter(education -> education.getDegree().equals(educationDto.getDegree())).findFirst();
            if (isDegreePresent.isPresent()) {
                throw new AlreadyFoundException("Degree already exists: " + degree.toString());
            }
        }

        var addEducation = educationMapper.addEducationDtoToEducation(educationDto);
        addEducation.setEmployee(employee);
        return educationRepository.save(addEducation);
    }

    @Override
    public Education update(EducationDto educationDto, UUID id) {
        Education education = getById(id);
        education.setDegree(educationDto.getDegree());
        education.setSchoolName(educationDto.getSchoolName());
        education.setGrade(educationDto.getGrade());
        education.setStartDate(educationDto.getStartDate());
        education.setEndDate(educationDto.getEndDate());

        return educationRepository.save(education);
    }

    @Override
    public Education getById(UUID id) {
        return educationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Education details not found with id: " + id));
    }

    @Override
    public List<Education> getAll(UUID employeeId) {
        List<Education> educations = educationRepository.getAllByEmployee_Uuid(employeeId);
        if (educations == null) {
            throw new NotFoundException("Education details not found");
        }
        return educations;
    }

    @Override
    public void deleteById(UUID id) {
        var education = getById(id);
        educationRepository.deleteById(id);
    }

}
