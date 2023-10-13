package com.learning.employemanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmployeManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeManagementSystemApplication.class, args);
    }

}
