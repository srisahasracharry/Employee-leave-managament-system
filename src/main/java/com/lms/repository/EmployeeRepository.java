package com.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> 
{
	 Employee findByName(String name);
}
