package com.learning.employemanagementsystem.service;

import com.learning.employemanagementsystem.dao.EducationDao;
import com.learning.employemanagementsystem.dao.EmployeeDao;
import com.learning.employemanagementsystem.dto.EducationDto;
import com.learning.employemanagementsystem.entity.EducationDegree;
import com.learning.employemanagementsystem.exception.AlreadyFoundException;
import com.learning.employemanagementsystem.exception.NotFoundException;
import com.learning.employemanagementsystem.mapper.EducationMapper;
import com.learning.employemanagementsystem.mapper.EmployeeMapper;
import com.learning.employemanagementsystem.model.EducationModel;
import com.learning.employemanagementsystem.model.EmployeeModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationDao educationDao;

    private final EducationMapper educationMapper;

    private final EmployeeMapper employeeMapper;

    private final EmployeeDao employeeDao;

    @Override
    public EducationModel save(EducationDto educationDto, UUID id) {
        if(!employeeDao.existsById(id)) {
            throw new NotFoundException("Education details not found with id: " + id);
        }
        EmployeeModel employee = employeeDao.getById(id);

        List<EducationModel> educations = educationDao.getAllByEmployeeId(id);
        EducationDegree degree = educationDto.getDegree();

        if (educations == null) {
            if (degree != EducationDegree.SSC_10TH) {
                throw new NotFoundException("Education Details of " + EducationDegree.SSC_10TH + " not found");
            }
        } else {
            Optional<EducationModel> isDegreePresent = educations.stream()
                    .filter(education -> education.getDegree().equals(educationDto.getDegree())).findFirst();
            if (isDegreePresent.isPresent()) {
                throw new AlreadyFoundException("Degree already exists: " + degree.toString());
            }
        }

        EducationModel addEducation = educationMapper.addEducationDtoToEducationModel(educationDto);
        addEducation.setEmployee(employeeMapper.employeeModelToEmployee(employee));
        return educationDao.save(addEducation);
    }

    @Override
    public EducationModel update(EducationDto educationDto, UUID id) {
        if(!educationDao.existsById(id)) {
            throw new NotFoundException("Education details not found with id: " + id);
        }
        EducationModel education = educationDao.getById(id);
        education.setDegree(educationDto.getDegree());
        education.setSchoolName(educationDto.getSchoolName());
        education.setGrade(educationDto.getGrade());
        education.setStartDate(educationDto.getStartDate());
        education.setEndDate(educationDto.getEndDate());

        return educationDao.save(education);
    }

    @Override
    public EducationModel get(UUID id) {
        if(educationDao.existsById(id)){
            return educationDao.getById(id);
        }
        throw new NotFoundException("Education details not found with id: " + id);
    }

    @Override
    public List<EducationModel> getAll(UUID employeeId) {
        List<EducationModel> educations = educationDao.getAllByEmployeeId(employeeId);
        if(educations == null) {
            throw new NotFoundException("Education details not  found");
        }
        return educations;
    }

    @Override
    public void deleteById(UUID id) {
        if(educationDao.existsById(id)){
            educationDao.deleteById(id);
        }
        else{
            throw new NotFoundException("Education details not found with id: " + id);
        }

    }

}
