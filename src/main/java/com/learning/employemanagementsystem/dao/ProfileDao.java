package com.learning.employemanagementsystem.dao;

import com.learning.employemanagementsystem.model.ProfileModel;

import java.util.UUID;

public interface ProfileDao {
    ProfileModel save(ProfileModel profile);

    ProfileModel getProfileByEmployeeId(UUID id);
}
