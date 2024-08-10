package com.lms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.model.Employee;
import com.lms.model.LeaveRequest;
import com.lms.repository.EmployeeRepository;
import com.lms.repository.LeaveRequestRepository;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    public LeaveRequest getLeaveRequestById(Long id) {
        return leaveRequestRepository.findById(id).orElse(null);
    }

    public void saveLeaveRequest(LeaveRequest leaveRequest) {
        leaveRequestRepository.save(leaveRequest);
    }

    public void approveLeaveRequest(Long requestId, Employee manager) {
        LeaveRequest leaveRequest = getLeaveRequestById(requestId);
        if (leaveRequest != null && "Pending".equals(leaveRequest.getStatus())) {
            leaveRequest.setApprover(manager);
            leaveRequest.setStatus("Approved");
            // Deduct leave balance
            Employee employee = leaveRequest.getEmployee();
            int leaveDays = (int) (leaveRequest.getEndDate().toEpochDay() - leaveRequest.getStartDate().toEpochDay() + 1);
            employee.setLeaveBalance(employee.getLeaveBalance() - leaveDays);
            employeeRepository.save(employee);
            saveLeaveRequest(leaveRequest);
        }
    }

    public void rejectLeaveRequest(Long requestId, Employee manager) {
        LeaveRequest leaveRequest = getLeaveRequestById(requestId);
        if (leaveRequest != null && "Pending".equals(leaveRequest.getStatus())) {
            leaveRequest.setApprover(manager);
            leaveRequest.setStatus("Rejected");
            saveLeaveRequest(leaveRequest);
        }
    }
}