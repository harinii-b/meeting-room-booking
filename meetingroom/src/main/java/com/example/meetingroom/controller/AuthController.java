package com.example.meetingroom.controller;
// import org.springframework.ui.Model; // Add this import

import com.example.meetingroom.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // Register API
    // @PostMapping("/register")
    // public ResponseEntity<Map<String, String>> register(
    //         @RequestParam String username,
    //         @RequestParam String password) {

    //     Map<String, String> response = new HashMap<>();

    //     if (userService.registerUser(username, password)) {
    //         response.put("message", "User registered successfully!");
    //         return ResponseEntity.ok(response);
    //     }

    //     response.put("message", "Username already exists!");
    //     return ResponseEntity.status(400).body(response);
    // }

    // Login API
    // @PostMapping("/login")
    // public ResponseEntity<Map<String, String>> login(
    //         @RequestParam String username,
    //         @RequestParam String password,
    //         HttpSession session) {

    //     Map<String, String> response = new HashMap<>();

    //     if (userService.loginUser(username, password, session)) {
    //         response.put("message", "Login successful!");
    //         return ResponseEntity.ok(response);
    //     }

    //     response.put("message", "Invalid credentials!");
    //     return ResponseEntity.status(401).body(response);
    // }

    // @PostMapping("/login")
    // public ResponseEntity<Map<String, String>> login(
    //         @RequestParam String username,
    //         @RequestParam String password,
    //         HttpSession session) {
    //     Map<String, String> response = new HashMap<>();
    //     if (userService.loginUser(username, password, session)) {
    //         response.put("message", "Login successful!");
    //         return ResponseEntity.ok(response);
    //     }
    //     response.put("message", "Invalid credentials!");
    //     return ResponseEntity.status(401).body(response);
    // }

    // Check who is logged in
@GetMapping("/whoami")
public ResponseEntity<Map<String, String>> getLoggedInUser(HttpSession session) {
    String loggedInUser = (String) session.getAttribute("loggedInUser");
    Map<String, String> response = new HashMap<>();
    if (loggedInUser != null) {
        response.put("username", loggedInUser);
        return ResponseEntity.ok(response);
    }
    response.put("message", "No user logged in");
    return ResponseEntity.status(401).body(response);
}

// Logout User
@GetMapping("/auth/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false); // Get the session if it exists
        if (session != null) {
            session.invalidate(); // Invalidate session
        }
        return "redirect:/login?logout"; // Redirect to login page with logout message
    }

}


