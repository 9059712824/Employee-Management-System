package com.learning.employemanagementsystem.service;

import com.learning.employemanagementsystem.dao.EmployeeDao;
import com.learning.employemanagementsystem.dao.ProfileDao;
import com.learning.employemanagementsystem.dto.AddEmployeeDto;
import com.learning.employemanagementsystem.dto.AddEmployeeResponseDto;
import com.learning.employemanagementsystem.dto.UpdateLeavingDate;
import com.learning.employemanagementsystem.entity.JobTitleType;
import com.learning.employemanagementsystem.entity.ProfileStatus;
import com.learning.employemanagementsystem.exception.AlreadyFoundException;
import com.learning.employemanagementsystem.exception.NotFoundException;
import com.learning.employemanagementsystem.mapper.EmployeeMapper;
import com.learning.employemanagementsystem.mapper.ProfileMapper;
import com.learning.employemanagementsystem.model.EmployeeModel;
import com.learning.employemanagementsystem.model.ProfileModel;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Random;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeDao employeeDao;

    private final ProfileDao profileDao;

    private final EmployeeMapper employeeMapper;

    private final ProfileMapper profileMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public AddEmployeeDto addEmployee(AddEmployeeDto employeeDto) {
        if(employeeDao.existsByEmail(employeeDto.getEmail())){
            throw new AlreadyFoundException("Email already exists: " + employeeDto.getEmail());
        }
        Period period = Period.between(employeeDto.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now());
        employeeDto.setAge(period.getYears());
        var employee = employeeMapper.addEmployeeDtoToEmployeeModel(employeeDto);
        employee.setPassword(passwordEncoder.encode(generateRandomPassword()));
        var updatedEmployee = employeeDao.save(employee);
        var profile = new ProfileModel();
        profile.setProfileStatus(ProfileStatus.PENDING);
        profile.setJobTitle(JobTitleType.ENGINEER_TRAINEE);
        profile.setEmployee(employeeMapper.employeeModelToEmployee(updatedEmployee));
        employee.setProfile(profileMapper.profileModelToProfile(profile));

        profileDao.save(profile);
        return employeeMapper.employeeModelToAddEmployeeDto(employee);
    }

    @Override
    public EmployeeModel viewEmployee(UUID id) {
        return employeeDao.getEmployeeById(id);
    }

    @Override
    public void updateLeavingDate(UUID id, UpdateLeavingDate updateLeavingDate) {
        EmployeeModel employee = employeeDao.getEmployeeById(id);
        if(employee == null) {
            throw new NotFoundException("employee not found with id: " + id);
        }
        employee.setLeavingDate(updateLeavingDate.getLeavingDate());
        employeeDao.save(employee);
    }

    public String generateRandomPassword() {
        String password = null;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < sb.capacity(); i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }
}
