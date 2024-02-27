package com.learning.employemanagementsystem.service.impl;

import com.learning.employemanagementsystem.entity.ProfileStatus;
import com.learning.employemanagementsystem.entity.Employee;
import com.learning.employemanagementsystem.repository.EmployeeRepository;
import com.learning.employemanagementsystem.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduledTasks {

    private final EmployeeRepository employeeRepository;

    private final ProfileRepository profileRepository;


    @Scheduled(cron = "0 0 0 * * ?")
    public void updateProfileStatusLeavingDate() {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            if (employee.getLeavingDate() != null && employee.getLeavingDate().before(new Date())) {
                var profile = employee.getProfile();
                if (employee.getLeavingDate() != null && profile.getProfileStatus() != ProfileStatus.INACTIVE) {
                    profile.setProfileStatus(ProfileStatus.INACTIVE);
                    profileRepository.save(profile);
                }
            }
        }
    }
}