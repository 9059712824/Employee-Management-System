package com.learning.employemanagementsystem.controller;

import com.learning.employemanagementsystem.dto.LoginDto;
import com.learning.employemanagementsystem.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody @Valid LoginDto loginDto) {
        return new ResponseEntity<>(authenticationService.login(loginDto), HttpStatus.OK);
    }
}
