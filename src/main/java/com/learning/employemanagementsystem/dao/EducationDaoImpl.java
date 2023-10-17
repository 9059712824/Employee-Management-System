package com.learning.employemanagementsystem.dao;

import com.learning.employemanagementsystem.mapper.EducationMapper;
import com.learning.employemanagementsystem.model.EducationModel;
import com.learning.employemanagementsystem.repository.EducationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class EducationDaoImpl implements EducationDao{

    private final EducationRepository educationRepository;

    private final EducationMapper educationMapper;
    @Override
    public EducationModel save(EducationModel educationModel) {
        return educationMapper.educationToEducationModel(educationRepository.save(educationMapper.educationModelToEducation(educationModel)));
    }

    @Override
    public List<EducationModel> getAllByEmployeeId(UUID id) {
        return educationMapper.educationListToEducationModelList(educationRepository.getAllByEmployee_Id(id));
    }

    @Override
    public EducationModel getById(UUID id) {
        return educationMapper.educationToEducationModel(educationRepository.getById(id));
    }

    @Override
    public void deleteById(UUID id) {
        educationRepository.deleteById(id);
    }

    @Override
    public Boolean existsById(UUID id) {
        return educationRepository.existsById(id);
    }
}
