package com.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lms.model.Employee;
import com.lms.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
    private EmployeeService employeeService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        Employee employee = employeeService.findByUsername(username);
        if (employee != null) {
            session.setAttribute("currentUser", employee);
            return "redirect:/leaves";
        }
        model.addAttribute("error", "Invalid username or password.");
        return "login";
    }
}
