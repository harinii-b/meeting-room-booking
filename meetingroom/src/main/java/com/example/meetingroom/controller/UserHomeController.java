// package com.example.meetingroom.controller;

// public class UserHomeController {
    
// }

package com.example.meetingroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserHomeController {
    
    @GetMapping("/userhome")
    public String showUserHomePage() {
        return "userhome"; // This will render userhome.html from templates folder
    }
}