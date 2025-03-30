package com.example.meetingroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.meetingroom.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/auth")  // Base path for all mappings in this controller
public class RegisterViewController {
    
    @Autowired
    private UserService userService;

    // Add this GET mapping to show the registration page
    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register";  // This will render register.html from templates folder
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {
        
        boolean registrationSuccess = userService.registerUser(username, password);
        
        if (registrationSuccess) {
            model.addAttribute("successMessage", "User registered successfully!");
            return "redirect:/auth/login";  // Redirect to login page after successful registration
        } else {
            model.addAttribute("errorMessage", "Username already exists!");
            return "register";  // Stay on registration page if registration fails
        }
    }
}