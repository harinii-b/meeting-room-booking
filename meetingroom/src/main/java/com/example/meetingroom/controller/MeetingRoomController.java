// package com.example.meetingroom.controller;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;

// import com.example.meetingroom.model.MeetingRoomBooking;
// import com.example.meetingroom.service.MeetingRoomService;

// import jakarta.servlet.http.HttpSession;
// import org.springframework.format.annotation.DateTimeFormat;
// import org.springframework.http.ResponseEntity;

// import java.time.LocalDateTime;
// import java.util.List;

// @Controller
// @RequestMapping("/meeting-room")
// public class MeetingRoomController {

//     private final MeetingRoomService meetingRoomService;

//     public MeetingRoomController(MeetingRoomService meetingRoomService) {
//         this.meetingRoomService = meetingRoomService;
//     }

//     @GetMapping
//     public String showMeetingRoomPage(Model model, HttpSession session) {
//         String username = (String) session.getAttribute("loggedInUser");

//         if (username == null) {
//             return "redirect:/auth/login";
//         }

//         List<MeetingRoomBooking> bookings = meetingRoomService.getAllBookings();
//         model.addAttribute("bookings", bookings);
//         return "meeting-room";
//     }

//     // @GetMapping("/book")
//     // @ResponseBody
//     // public List<MeetingRoomBooking> getAllBookings() {
//     //     return meetingRoomService.getAllBookings();
//     // }

// //     @GetMapping("/book")
// // @ResponseBody
// // public List<MeetingRoomBooking> getAllBookings() {
// //     List<MeetingRoomBooking> bookings = meetingRoomService.getAllBookings();
// //     System.out.println("Fetched Bookings: " + bookings.size());  // Add logging
// //     return bookings;
// // }


// @GetMapping("/book")
// public ResponseEntity<List<MeetingRoomBooking>> getAllBookings(
//         @RequestParam String roomName,
//         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
//         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
    
//     List<MeetingRoomBooking> bookings = meetingRoomService.getUserOverlappingBookings(roomName, startTime, endTime);
//     return ResponseEntity.ok(bookings);
// }


//     @PostMapping("/book")
//     public String bookMeetingRoom(
//             @RequestParam String roomName,
//             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
//             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
//             HttpSession session,
//             Model model) {

//         String username = (String) session.getAttribute("loggedInUser");

//         if (username == null) {
//             model.addAttribute("errorMessage", "You must be logged in to book a room.");
//             return "meeting-room";
//         }

//         boolean success = meetingRoomService.bookRoom(roomName, startTime, endTime, username);

//         if (success) {
//             model.addAttribute("successMessage", "Room booked successfully!");
//         } else {
//             model.addAttribute("errorMessage", "Room is already booked for the selected time.");
//         }

//         List<MeetingRoomBooking> bookings = meetingRoomService.getAllBookings();
//         model.addAttribute("bookings", bookings);
//         return "meeting-room";
//     }
// }


package com.example.meetingroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.meetingroom.model.AdminMeetingRoom;
import com.example.meetingroom.model.MeetingRoomBooking;
import com.example.meetingroom.service.MeetingRoomService;

import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/meeting-room")
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;

    public MeetingRoomController(MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    }

    @GetMapping
    public String showMeetingRoomPage(Model model, HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");

        if (username == null) {
            return "redirect:/auth/login";
        }

        // Get all meeting rooms and bookings from the service
        List<AdminMeetingRoom> meetingRooms = meetingRoomService.getAllMeetingRooms();
        List<MeetingRoomBooking> bookings = meetingRoomService.getAllBookings();
        
        // Add both to the model
        model.addAttribute("meetingRooms", meetingRooms);
        model.addAttribute("bookings", bookings);
        
        return "meeting-room";
    }

    @GetMapping("/book")
    public ResponseEntity<List<MeetingRoomBooking>> getAllBookings(
            @RequestParam String roomName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        
        List<MeetingRoomBooking> bookings = meetingRoomService.getUserOverlappingBookings(roomName, startTime, endTime);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/book")
    public String bookMeetingRoom(
            @RequestParam String roomName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            HttpSession session,
            Model model) {

        String username = (String) session.getAttribute("loggedInUser");

        if (username == null) {
            model.addAttribute("errorMessage", "You must be logged in to book a room.");
            return "meeting-room";
        }

        boolean success = meetingRoomService.bookRoom(roomName, startTime, endTime, username);

        if (success) {
            model.addAttribute("successMessage", "Room booked successfully!");
        } else {
            model.addAttribute("errorMessage", "Room is already booked for the selected time.");
        }

        // Get all meeting rooms and bookings again to refresh the view
        List<AdminMeetingRoom> meetingRooms = meetingRoomService.getAllMeetingRooms();
        List<MeetingRoomBooking> bookings = meetingRoomService.getAllBookings();
        
        model.addAttribute("meetingRooms", meetingRooms);
        model.addAttribute("bookings", bookings);
        
        return "meeting-room";
    }
}