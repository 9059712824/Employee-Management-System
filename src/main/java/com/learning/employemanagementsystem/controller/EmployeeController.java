package com.learning.employemanagementsystem.controller;

import com.learning.employemanagementsystem.dto.AddEmployeeDto;
import com.learning.employemanagementsystem.dto.UpdateLeavingDate;
import com.learning.employemanagementsystem.service.EmployeeService;
import com.learning.employemanagementsystem.service.ExcelService;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController("/employee")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    private final ExcelService excelService;

    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody AddEmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.add(employeeDto), HttpStatus.CREATED);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<?> viewEmployee(@PathVariable UUID id) {
        return new ResponseEntity<>(employeeService.view(id), HttpStatus.OK);
    }

    @GetMapping("/viewAll")
    public ResponseEntity<?> viewAllEmployees() {
        return new ResponseEntity<>(employeeService.viewAll(), HttpStatus.OK);
    }

    @PostMapping("/updateLeavingDate/{id}")
    public ResponseEntity<?> updateLeavingStatus(@PathVariable UUID id, @RequestBody UpdateLeavingDate updateLeavingDate) {
        employeeService.updateLeavingDate(id, updateLeavingDate);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> fileRead(@RequestParam(name = "file") MultipartFile file,
                                      @RequestParam(required = false, defaultValue = "1") String sheetNumber) throws IOException {
        excelService.readFile(file, sheetNumber);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
