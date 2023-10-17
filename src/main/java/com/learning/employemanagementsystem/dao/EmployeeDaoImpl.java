package com.learning.employemanagementsystem.dao;

import com.learning.employemanagementsystem.mapper.EmployeeMapper;
import com.learning.employemanagementsystem.model.EmployeeModel;
import com.learning.employemanagementsystem.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class EmployeeDaoImpl implements EmployeeDao {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;
    @Override
    public Boolean existsByEmail(String email) {
        if(employeeRepository.existsByEmail(email)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean existsById(UUID id) {
        return employeeRepository.existsById(id);
    }

    @Override
    public EmployeeModel save(EmployeeModel employeeModel) {
        return employeeMapper.employeeToEmployeeModel(employeeRepository.save(employeeMapper.employeeModelToEmployee(employeeModel)));
    }

    @Override
    public EmployeeModel getById(UUID id) {
        return employeeMapper.employeeToEmployeeModel(employeeRepository.getReferenceById(id));
    }

    @Override
    public List<EmployeeModel> getAll() {
        System.out.println(employeeRepository.findAll());
        return employeeMapper.employeeListToEmployeeMOdelList(employeeRepository.findAll());
    }

    @Override
    public EmployeeModel getEmployeeById(UUID id) {
        return employeeMapper.employeeToEmployeeModel(employeeRepository.getReferenceById(id));
    }

    @Override
    public EmployeeModel getByEmail(String email) {
        return employeeMapper.employeeToEmployeeModel(employeeRepository.getByEmail(email));
    }
}
