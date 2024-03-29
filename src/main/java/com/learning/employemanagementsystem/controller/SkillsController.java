package com.learning.employemanagementsystem.controller;

import com.learning.employemanagementsystem.dto.SkillsDto;
import com.learning.employemanagementsystem.entity.Skills;
import com.learning.employemanagementsystem.service.SkillsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leave")
public class SkillsController {

    private final SkillsService skillsService;

    @PostMapping("/add-skills")
    public ResponseEntity<Skills> add(@RequestBody SkillsDto skillsDto) {
        return new ResponseEntity<>(skillsService.add(skillsDto), HttpStatus.CREATED);
    }

    @PutMapping("/update-skills{skillsUuid}")
    public ResponseEntity<Skills> update(@PathVariable UUID skillsUuid, @RequestBody SkillsDto skillsDto) {
        return new ResponseEntity<>(skillsService.update(skillsUuid, skillsDto), HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-skills-by-id/{skillsUuid}")
    public ResponseEntity<Skills> gteById(@PathVariable UUID skillsUuid) {
        return new ResponseEntity<>(skillsService.getById(skillsUuid), HttpStatus.OK);
    }

    @GetMapping("/get-all-skills-by-employeeUuid/{employeeUuid}")
    public ResponseEntity<List<Skills>> getAllBYColleagueUuid(@PathVariable UUID employeeUuid) {
        return new ResponseEntity<>(skillsService.getAll(employeeUuid), HttpStatus.OK);
    }

    @DeleteMapping("/delete-skills-by-id/{skillsUuid}/employee/{employeeUuid}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable UUID employeeUuid, @PathVariable UUID skillsUuid) {
        skillsService.deleteById(skillsUuid, employeeUuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
