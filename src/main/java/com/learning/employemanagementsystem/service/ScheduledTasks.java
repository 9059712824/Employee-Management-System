package com.learning.employemanagementsystem.service;

import com.learning.employemanagementsystem.dao.EmployeeDao;
import com.learning.employemanagementsystem.dao.ProfileDao;
import com.learning.employemanagementsystem.entity.ProfileStatus;
import com.learning.employemanagementsystem.mapper.EmployeeMapper;
import com.learning.employemanagementsystem.mapper.ProfileMapper;
import com.learning.employemanagementsystem.model.EmployeeModel;
import com.learning.employemanagementsystem.model.ProfileModel;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduledTasks {

    private final EmployeeDao employeeDao;

    private final ProfileDao profileDao;

    private final ProfileMapper profileMapper;


    @Scheduled(cron = "0 0 0 * * ?")
    public void updateProfileStatusLeavingDate() {
        List<EmployeeModel> employees = employeeDao.getAll();
        for (EmployeeModel employee : employees) {
            if (employee.getLeavingDate() != null && employee.getLeavingDate().before(new Date())) {
                ProfileModel profile = profileMapper.profileToProfileModel(employee.getProfile());
                if (employee.getLeavingDate() != null && profile.getProfileStatus() != ProfileStatus.INACTIVE) {
                    profile.setProfileStatus(ProfileStatus.INACTIVE);
                    profileDao.save(profile);
                }
            }
        }
    }
}