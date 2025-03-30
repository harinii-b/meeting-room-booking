package com.example.meetingroom.service;

import com.example.meetingroom.model.User;
import com.example.meetingroom.repository.UserRepository;
// import jakarta.servlet.http.HttpSession;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import java.util.Optional;

// @Service
// public class UserService {
//     @Autowired
//     private UserRepository userRepository;

//     public User registerUser(String username, String password) {
//         if (userRepository.findByUsername(username).isPresent()) {
//             throw new RuntimeException("Username already exists!");
//         }
//         return userRepository.save(new User(username, password)); // Storing plain text for now
//     }

//     public boolean loginUser(String username, String password, HttpSession session) {
//         Optional<User> user = userRepository.findByUsername(username);
//         if (user.isPresent() && user.get().getPassword().equals(password)) {
//             session.setAttribute("user", user.get()); // Store user in session
//             return true;
//         }
//         return false;
//     }

//     public void logoutUser(HttpSession session) {
//         session.invalidate(); // Clear session
//     }
// }


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register User
    public boolean registerUser(String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            return false; // User already exists
        }
        User newUser = new User(username, password);
        userRepository.save(newUser);
        return true;
    }

    // Login User
    public boolean loginUser(String username, String password, HttpSession session) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loggedInUser", username); // Store user session
            return true;
        }
        return false;
    }
}
