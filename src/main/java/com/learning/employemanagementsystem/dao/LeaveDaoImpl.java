package com.learning.employemanagementsystem.dao;

import com.learning.employemanagementsystem.repository.LeaveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class LeaveDaoImpl implements LeaveDao {

    private final LeaveRepository leaveRepository;
}
