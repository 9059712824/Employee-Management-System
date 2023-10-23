package com.learning.employemanagementsystem.repository;

import com.learning.employemanagementsystem.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Profile getByEmployee_Uuid(UUID uuid);


}
