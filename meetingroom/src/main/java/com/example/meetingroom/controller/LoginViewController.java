package com.example.meetingroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.meetingroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class LoginViewController {

    @Autowired
    private UserService userService;

    // GET endpoint to show login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // POST endpoint to handle login
    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        
        if (userService.loginUser(username, password, session)) {
            // Redirect to dashboard or home page on successful login
            session.setAttribute("loggedInUser", username);
            return "redirect:/userhome";
        }
        
        // Add error message and return to login page
        model.addAttribute("error", "Invalid credentials!");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false); // Get the session if it exists
        if (session != null) {
            session.invalidate(); // Invalidate session
        }
        // return "redirect:/login?logout"; // Redirect to login page with logout message
        return "login";
    }
}