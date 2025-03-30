package com.example.meetingroom;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
// public class MeetingroomApplication {

// 	public static void main(String[] args) {
// 		SpringApplication.run(MeetingroomApplication.class, args);
// 	}

// }

// package com.example;

import com.example.meetingroom.client.MeetingRoomAuthUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import javax.swing.*;

@SpringBootApplication
public class MeetingroomApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeetingroomApplication.class, args);
    }

    @Bean
    public CommandLineRunner launchSwingUI() {
        return args -> {
            SwingUtilities.invokeLater(() -> {
                MeetingRoomAuthUI authUI = new MeetingRoomAuthUI();
                authUI.setVisible(true);
            });
        };
    }
}