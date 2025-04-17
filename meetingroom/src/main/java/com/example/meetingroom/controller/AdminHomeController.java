// package com.example.meetingroom.controller;

// public class AdminHomeController {
    
// }

// package com.example.meetingroom.controller;

// public class UserHomeController {
    
// }

package com.example.meetingroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminHomeController {
    
    @GetMapping("/adminhome")
    public String showAdminHomePage() {
        return "adminhome"; // this will render .html from templates folder
    }
}