package com.learning.employemanagementsystem.service;

import com.learning.employemanagementsystem.dto.LoginDto;

import java.util.UUID;

public interface AuthenticationService {
    UUID login(LoginDto loginDto);
}
