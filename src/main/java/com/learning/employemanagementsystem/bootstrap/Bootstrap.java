package com.learning.employemanagementsystem.bootstrap;

import com.learning.employemanagementsystem.entity.Employee;
import com.learning.employemanagementsystem.entity.Gender;
import com.learning.employemanagementsystem.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        if (employeeRepository.count() < 3) {
            var employee1 = employeeRepository.save(Employee.builder()
                    .firstName("venky")
                    .lastName("repalle")
                    .email("rvkrishna13052001@gmail.com")
                    .phoneNumber("9059712824")
                    .dateOfBirth(Date.valueOf("2001-05-13"))
                    .age(23)
                    .joiningDate(Date.valueOf("2022-07-04"))
                    .gender(Gender.MALE)
                    .isManager(Boolean.TRUE)
                    .password(passwordEncoder.encode("venky123"))
                    .build());

            var employee2 = employeeRepository.save(Employee.builder()
                    .firstName("chandu")
                    .lastName("raya")
                    .email("chandu.raya@gmail.com")
                    .phoneNumber("6305177093")
                    .dateOfBirth(Date.valueOf("2000-11-01"))
                    .age(23)
                    .joiningDate(Date.valueOf("2022-07-04"))
                    .gender(Gender.MALE)
                    .isManager(Boolean.FALSE)
                    .password(passwordEncoder.encode("chandu123"))
                    .manager(employee1)
                    .build());

            var employee3 = employeeRepository.save(Employee.builder()
                    .firstName("sujith")
                    .lastName("bikki")
                    .email("sujith.bikki@gmail.com")
                    .phoneNumber("1234567890")
                    .dateOfBirth(Date.valueOf("2000-05-14"))
                    .age(24)
                    .joiningDate(Date.valueOf("2022-07-04"))
                    .gender(Gender.MALE)
                    .isManager(Boolean.FALSE)
                    .password(passwordEncoder.encode("sujith123"))
                    .manager(employee1)
                    .build());

            var employee4 = employeeRepository.save(Employee.builder()
                    .firstName("veeranji")
                    .lastName("katari")
                    .email("veeranji.katari@gmail.com")
                    .phoneNumber("9490903106")
                    .dateOfBirth(Date.valueOf("2000-11-18"))
                    .age(24)
                    .joiningDate(Date.valueOf("2022-07-04"))
                    .gender(Gender.MALE)
                    .password(passwordEncoder.encode("veeranji123"))
                    .isManager(Boolean.TRUE)
                    .build());

            var employee5 = employeeRepository.save(Employee.builder()
                    .firstName("lakshman")
                    .lastName("jampani")
                    .email("lakshman.jampani@gmail.com")
                    .phoneNumber("6303537771")
                    .dateOfBirth(Date.valueOf("2001-02-28"))
                    .age(24)
                    .joiningDate(Date.valueOf("2022-07-04"))
                    .gender(Gender.MALE)
                    .isManager(Boolean.FALSE)
                    .manager(employee4)
                    .password(passwordEncoder.encode("lakshman123"))
                    .build());
        }
    }
}
