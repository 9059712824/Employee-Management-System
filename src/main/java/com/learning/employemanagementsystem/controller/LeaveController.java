package com.learning.employemanagementsystem.controller;

import com.learning.employemanagementsystem.service.LeaveService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/leave")
@AllArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

//    @PostMapping("/applyLeave")
//    public ResponseEntity<?> add(@RequestBody)
}
