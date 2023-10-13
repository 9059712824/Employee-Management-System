package com.learning.employemanagementsystem.dao;

import com.learning.employemanagementsystem.mapper.ProfileMapper;
import com.learning.employemanagementsystem.model.ProfileModel;
import com.learning.employemanagementsystem.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@AllArgsConstructor
public class ProfileDaoImpl implements ProfileDao{

    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;
    @Override
    public ProfileModel save(ProfileModel profile) {
        return profileMapper.profileToProfileModel(profileRepository.save(profileMapper.profileModelToProfile(profile)));
    }

    @Override
    public ProfileModel getProfileByEmployeeId(UUID id) {
        return profileMapper.profileToProfileModel(profileRepository.getByEmployee_Id(id));
    }
}
