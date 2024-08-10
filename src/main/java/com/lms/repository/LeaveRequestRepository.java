package com.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.model.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
	
}
