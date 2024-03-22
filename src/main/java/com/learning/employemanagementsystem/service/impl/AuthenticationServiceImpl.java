package com.learning.employemanagementsystem.service.impl;

import com.learning.employemanagementsystem.dto.LoginDto;
import com.learning.employemanagementsystem.exception.InvalidInputException;
import com.learning.employemanagementsystem.exception.NotFoundException;
import com.learning.employemanagementsystem.repository.EmployeeRepository;
import com.learning.employemanagementsystem.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final EmployeeRepository employeeRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UUID login(LoginDto loginDto) {
        if (Boolean.FALSE.equals(employeeRepository.existsByEmail(loginDto.getEmail()))) {
            throw new NotFoundException("Email Not exists " + loginDto.getEmail());
        }
        var employee = employeeRepository.getByEmail(loginDto.getEmail());

        if (!passwordEncoder.matches(loginDto.getPassword(), employee.getPassword())) {
            throw new InvalidInputException("Incorrect password");
        }
        return employee.getUuid();
    }
}
