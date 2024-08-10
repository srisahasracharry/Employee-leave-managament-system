package com.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.model.Employee;
import com.lms.repository.EmployeeRepository;

@Service
public class EmployeeService 
{
	@Autowired
    private EmployeeRepository employeeRepository;

    public Employee findByUsername(String username) {
        return employeeRepository.findByName(username);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }
}
