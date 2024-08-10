package com.lms.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lms.model.Employee;
import com.lms.model.LeaveRequest;
import com.lms.service.EmployeeService;
import com.lms.service.LeaveRequestService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/leaves")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;


    @GetMapping
    public String listLeaveRequests(HttpSession session, Model model) {
        Employee currentUser = (Employee) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("leaveRequests", leaveRequestService.getAllLeaveRequests());
        model.addAttribute("currentUser", currentUser);
        return "leave/list";
    }

    @GetMapping("/details")
    public String viewLeaveRequest(@RequestParam Long id, HttpSession session, Model model) {
        Employee currentUser = (Employee) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        LeaveRequest leaveRequest = leaveRequestService.getLeaveRequestById(id);
        model.addAttribute("leaveRequest", leaveRequest);
        model.addAttribute("currentUser", currentUser);
        return "leave/details";
    }

    @GetMapping("/request")
    public String showRequestForm(HttpSession session, Model model) {
        Employee currentUser = (Employee) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("currentUser", currentUser);
        return "leave/request";
    }

    @PostMapping("/request")
    public String submitLeaveRequest(@RequestParam LocalDate startDate,
                                     @RequestParam LocalDate endDate,
                                     HttpSession session) {
        Employee currentUser = (Employee) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setStartDate(startDate);
        leaveRequest.setEndDate(endDate);
        leaveRequest.setStatus("Pending");
        leaveRequest.setEmployee(currentUser);

        leaveRequestService.saveLeaveRequest(leaveRequest);
        return "redirect:/leaves";
    }

    @PostMapping("/approve")
    public String approveLeaveRequest(@RequestParam Long requestId, HttpSession session) {
        Employee currentUser = (Employee) session.getAttribute("currentUser");
        if (currentUser == null || !"MANAGER".equals(currentUser.getRole())) {
            return "redirect:/login";
        }
        leaveRequestService.approveLeaveRequest(requestId, currentUser);
        return "redirect:/leaves";
    }

    @PostMapping("/reject")
    public String rejectLeaveRequest(@RequestParam Long requestId, HttpSession session) {
        Employee currentUser = (Employee) session.getAttribute("currentUser");
        if (currentUser == null || !"MANAGER".equals(currentUser.getRole())) {
            return "redirect:/login";
        }
        leaveRequestService.rejectLeaveRequest(requestId, currentUser);
        return "redirect:/leaves";
    }
}