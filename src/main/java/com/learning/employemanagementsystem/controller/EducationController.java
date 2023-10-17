package com.learning.employemanagementsystem.controller;

import com.learning.employemanagementsystem.dto.EducationDto;
import com.learning.employemanagementsystem.service.EducationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController("/education")
@AllArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @PostMapping("/addEducation/{employeeId}")
    public ResponseEntity<?> add(@RequestBody EducationDto educationDto, @PathVariable UUID employeeId){
        return new ResponseEntity<>(educationService.save(educationDto, employeeId), HttpStatus.CREATED);
    }

    @PutMapping("/updateEducation/{id}")
    public ResponseEntity<?> update(@RequestBody EducationDto educationDto, @PathVariable UUID id) {
        return new ResponseEntity<>(educationService.update(educationDto, id), HttpStatus.CREATED);
    }

    @GetMapping("/getEducationDetails/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        return new ResponseEntity<>(educationService.get(id), HttpStatus.OK);
    }

    @GetMapping("/getAllEducationDetails/{employeeId}")
    public ResponseEntity<?> getAll(@PathVariable UUID employeeId) {
        return new ResponseEntity<>(educationService.getAll(employeeId), HttpStatus.OK);
    }

    @DeleteMapping("deleteEducationDetails/{id}")
    public  ResponseEntity<?> deleteById(@PathVariable UUID id) {
        educationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
