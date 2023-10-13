package com.learning.employemanagementsystem.controller;

import com.learning.employemanagementsystem.dto.AddEmployeeDto;
import com.learning.employemanagementsystem.dto.UpdateLeavingDate;
import com.learning.employemanagementsystem.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/addEmployee")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody AddEmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.addEmployee(employeeDto), HttpStatus.CREATED);
    }

    @GetMapping("/viewEmployee/{id}")
    public ResponseEntity<?> viewEmployee(@PathVariable UUID id) {
        return new ResponseEntity<>(employeeService.viewEmployee(id), HttpStatus.OK);
    }

    @PostMapping("/updateLeavingDate/{id}")
    public ResponseEntity<?> updateLeavingStatus(@PathVariable UUID id, @RequestBody UpdateLeavingDate updateLeavingDate) {
        employeeService.updateLeavingDate(id, updateLeavingDate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
