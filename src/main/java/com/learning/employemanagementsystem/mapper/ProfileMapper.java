package com.learning.employemanagementsystem.mapper;

import com.learning.employemanagementsystem.entity.Profile;
import com.learning.employemanagementsystem.model.ProfileModel;
import org.mapstruct.Mapper;

@Mapper
public interface ProfileMapper {
    ProfileModel profileToProfileModel(Profile profile);

    Profile profileModelToProfile(ProfileModel profileModel);
}
