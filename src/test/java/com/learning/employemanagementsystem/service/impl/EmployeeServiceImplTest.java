package com.learning.employemanagementsystem.service.impl;

import com.learning.employemanagementsystem.dto.AddEmployeeDto;
import com.learning.employemanagementsystem.dto.UpdateLeavingDate;
import com.learning.employemanagementsystem.entity.Employee;
import com.learning.employemanagementsystem.entity.Gender;
import com.learning.employemanagementsystem.entity.Profile;
import com.learning.employemanagementsystem.exception.AlreadyFoundException;
import com.learning.employemanagementsystem.exception.NotFoundException;
import com.learning.employemanagementsystem.mapper.EmployeeMapper;
import com.learning.employemanagementsystem.repository.EmployeeRepository;
import com.learning.employemanagementsystem.repository.ProfileRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    AddEmployeeDto addEmployeeDto = AddEmployeeDto.builder()
            .firstName("test1")
            .lastName("test1")
            .email("rvkrishna13052001@gmail.com")
            .dateOfBirth(new Date())
            .gender(Gender.MALE)
            .phoneNumber("9059712824")
            .joiningDate(new Date())
            .build();

    @Test
    void addSuccess() {
        when(employeeRepository.existsByEmail(any())).thenReturn(false);

        when(employeeMapper.addEmployeeDtoToEmployee(any())).thenReturn(new Employee());
        when(employeeMapper.EmployeeToAddEmployeeDto(any())).thenReturn(new AddEmployeeDto());

        when(employeeRepository.save(any(Employee.class))).thenReturn(new Employee());

        employeeService.add(addEmployeeDto);

        verify(employeeRepository).existsByEmail(anyString());
        verify(employeeMapper).addEmployeeDtoToEmployee(any());

        verify(employeeRepository).save(any(Employee.class));
        verify(profileRepository).save(any(Profile.class));

    }

    @Test
    void addExpect() {
        when(employeeRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(AlreadyFoundException.class, () ->
                employeeService.add(addEmployeeDto));
    }

    @Test
    void getByIdSuccess() {
        when(employeeRepository.findById(any())).thenReturn(Optional.of(new Employee()));

        Employee result = employeeService.getById(UUID.randomUUID());
        assertNotNull(result);

        verify(employeeRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void getByIdExpect() {
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> employeeService.getById(UUID.randomUUID()));

        verify(employeeRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void updateLeavingDateSuccess() {
        when(employeeRepository.findById(any())).thenReturn(Optional.of(new Employee()));
        when(employeeRepository.save(any(Employee.class))).thenReturn(new Employee());

        employeeService.updateLeavingDate(UUID.randomUUID(), UpdateLeavingDate.builder().leavingDate(new Date()).build());

        verify(employeeRepository, times(1)).findById(any(UUID.class));
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void viewAllSuccess() {
        List<Employee> employee = new ArrayList<>();
        when(employeeRepository.findAll()).thenReturn(employee);

        employeeService.viewAll();

        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void getByManagerUuidSuccess() {
        List<Employee> employeesList = new ArrayList<>();

        UUID uuid = UUID.randomUUID();

        Employee manager = Employee.builder()
                .isManager(Boolean.TRUE)
                .uuid(uuid).build();

//        when(employeeRepository.findById(uuid)).thenReturn(Optional.of(new Employee()));
        when(manager.getIsManager()).thenReturn(Boolean.TRUE);
        when(employeeRepository.getAllByManagerUuid(uuid)).thenReturn(employeesList);

        List<Employee> result = employeeService.getByManagerUuid(uuid);
        assertEquals(employeesList, result);

        verify(employeeRepository, times(1)).findById(any(UUID.class));
        verify(employeeRepository, times(1)).getAllByManagerUuid(any(UUID.class));
    }


    @Test
    void getByEmailSuccess() {
        when(employeeRepository.getByEmail(anyString())).thenReturn(new Employee());

        employeeService.getByEmail(addEmployeeDto.getEmail());

        verify(employeeRepository, times(1)).getByEmail(anyString());
    }

    @Test
    void getByEmailExpect() {
        when(employeeRepository.getByEmail(anyString())).thenReturn(null);

        assertThrows(NotFoundException.class, () ->
                employeeService.getByEmail(anyString()));

        verify(employeeRepository, times(1)).getByEmail(anyString());
    }

    @Test
    void existsByEmailSuccess() {
        when(employeeRepository.existsByEmail(anyString())).thenReturn(Boolean.TRUE);

        employeeService.existsByEmail(anyString());

        verify(employeeRepository, times(1)).existsByEmail(anyString());
    }

    @Test
    void existsByEmailFailure() {
        when(employeeRepository.existsByEmail(anyString())).thenReturn(Boolean.FALSE);

        assertThrows(NotFoundException.class, () ->
                employeeService.existsByEmail(anyString()));

        verify(employeeRepository, times(1)).existsByEmail(anyString());
    }
}