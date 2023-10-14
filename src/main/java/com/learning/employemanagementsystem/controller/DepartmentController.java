package com.learning.employemanagementsystem.controller;

import com.learning.employemanagementsystem.dto.AddDepartmentDto;
import com.learning.employemanagementsystem.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController("/department")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    @PostMapping("/addDepartment")
    public ResponseEntity<?> add(@RequestBody AddDepartmentDto department) {
        return new ResponseEntity<>(departmentService.add(department), HttpStatus.CREATED);
    }

    @PostMapping(value = "/departmentPermission/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> departmentPermission(@RequestParam(name = "file") MultipartFile file) throws IOException {
        departmentService.departmentPermission(file);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}

