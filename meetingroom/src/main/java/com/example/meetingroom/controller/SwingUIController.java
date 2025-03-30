package com.example.meetingroom.controller;
// package com.example.meetingroom.controller;

import com.example.meetingroom.client.MeetingRoomAuthUI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.swing.*;

@Controller
public class SwingUIController {

    @GetMapping("/")
    public String showSwingUI(Model model) {
        // Launch Swing UI
        SwingUtilities.invokeLater(() -> {
            MeetingRoomAuthUI authUI = new MeetingRoomAuthUI();
            authUI.setVisible(true);
        });

        // Return Thymeleaf template
        return "swing-ui";
    }
}