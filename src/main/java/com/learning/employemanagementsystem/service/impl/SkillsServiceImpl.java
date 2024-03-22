package com.learning.employemanagementsystem.service.impl;

import com.learning.employemanagementsystem.dto.SkillsDto;
import com.learning.employemanagementsystem.entity.Skills;
import com.learning.employemanagementsystem.exception.InvalidInputException;
import com.learning.employemanagementsystem.exception.NotFoundException;
import com.learning.employemanagementsystem.mapper.SkillsMapper;
import com.learning.employemanagementsystem.repository.SkillsRepository;
import com.learning.employemanagementsystem.service.EmployeeService;
import com.learning.employemanagementsystem.service.SkillsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SkillsServiceImpl implements SkillsService {

    private final EmployeeService employeeService;

    private final SkillsMapper skillsMapper;

    private final SkillsRepository skillsRepository;

    @Override
    public Skills add(SkillsDto skillsDto) {
        inputValidation(skillsDto);
        isSkillExists(skillsDto.getName().trim(), skillsDto.getEmployeeUuid());
        
        var employee = employeeService.getById(skillsDto.getEmployeeUuid());
        var skills = skillsMapper.skillsDtoToSkills(skillsDto);
        skills.setName(skills.getName().trim());
        skills.setEmployee(employee);
        return skillsRepository.save(skills);
    }

    @Override
    public Skills update(UUID skillsUuid, SkillsDto skillsDto) {
        inputValidation(skillsDto);
        var employee = employeeService.getById(skillsDto.getEmployeeUuid());
        var skills = getById(skillsUuid);

        if (Boolean.FALSE.equals(employee.getUuid().equals(skills.getEmployee().getUuid()))) {
            throw new InvalidInputException("Employee is not eligible to update skills");
        }
        skills.setRating(skillsDto.getRating());

        return skillsRepository.save(skills);
    }

    @Override
    public Skills getById(UUID skillsUuid) {
        return skillsRepository.findById(skillsUuid)
                .orElseThrow(() -> new NotFoundException("Skills not found with id " + skillsUuid));
    }

    @Override
    public List<Skills> getAll(UUID employeeUuid) {
        employeeService.getById(employeeUuid);
        return skillsRepository.getSkillsByEmployee_Uuid(employeeUuid);
    }

    @Override
    public void deleteById(UUID skillsUuid, UUID employeeUuid) {
        employeeService.getById(employeeUuid);
        var skills = getById(skillsUuid);
        if (!skills.getEmployee().getUuid().equals(employeeUuid)) {
            throw new InvalidInputException("Employee is not eligible to delete this skill");
        }
        skills.getEmployee().getSkills().remove(skills);
        skillsRepository.deleteById(skillsUuid);
    }

    private void inputValidation(SkillsDto skillsDto) {
        if (skillsDto.getRating() < 1 || skillsDto.getRating() > 10) {
            throw new InvalidInputException("Ratings must between 1 and 10");
        }
    }

    private void isSkillExists(String name, UUID employeeUuid) {
        var skills = skillsRepository.getSkillsByNameAndEmployee_Uuid(name, employeeUuid);

        for (var skill : skills) {
            if (name.equalsIgnoreCase(skill.getName().toLowerCase())) {
                throw new InvalidInputException("Skill already exists");
            }
        }
    }
}
