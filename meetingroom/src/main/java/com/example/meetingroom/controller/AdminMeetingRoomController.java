// // package com.example.meetingroom.controller;

// // public class AdminMeetingRoomController {
    
// // }

// package com.example.meetingroom.controller;

// import com.example.meetingroom.model.AdminMeetingRoom;
// import com.example.meetingroom.service.AdminMeetingRoomService;
// import jakarta.validation.Valid;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/admin/meeting-rooms")
// public class AdminMeetingRoomController {
    
//     private final AdminMeetingRoomService adminMeetingRoomService;
    
//     @Autowired
//     public AdminMeetingRoomController(AdminMeetingRoomService adminMeetingRoomService) {
//         this.adminMeetingRoomService = adminMeetingRoomService;
//     }
    
//     // Add a new meeting room
//     @PostMapping
//     public ResponseEntity<?> addMeetingRoom(@Valid @RequestBody AdminMeetingRoom adminMeetingRoom) {
//         try {
//             AdminMeetingRoom newRoom = adminMeetingRoomService.addMeetingRoom(adminMeetingRoom);
//             return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
//         } catch (IllegalArgumentException e) {
//             return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//         }
//     }
    
//     // Get all meeting rooms
//     @GetMapping
//     public ResponseEntity<List<AdminMeetingRoom>> getAllMeetingRooms() {
//         List<AdminMeetingRoom> rooms = adminMeetingRoomService.getAllMeetingRooms();
//         return new ResponseEntity<>(rooms, HttpStatus.OK);
//     }
// }

package com.example.meetingroom.controller;

import com.example.meetingroom.model.AdminMeetingRoom;
import com.example.meetingroom.service.AdminMeetingRoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/meeting-rooms")
public class AdminMeetingRoomController {
    
    private final AdminMeetingRoomService adminMeetingRoomService;
    
    @Autowired
    public AdminMeetingRoomController(AdminMeetingRoomService adminMeetingRoomService) {
        this.adminMeetingRoomService = adminMeetingRoomService;
    }
    
    @GetMapping
    public String viewMeetingRooms(Model model) {
        List<AdminMeetingRoom> rooms = adminMeetingRoomService.getAllMeetingRooms();
        model.addAttribute("rooms", rooms);
        
        // Add empty meeting room object for the form
        if (!model.containsAttribute("adminMeetingRoom")) {
            model.addAttribute("adminMeetingRoom", new AdminMeetingRoom());
        }
        
        return "admin-meeting-room";
    }
    
    @PostMapping
    public String addMeetingRoom(@Valid @ModelAttribute("adminMeetingRoom") AdminMeetingRoom adminMeetingRoom, 
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        
        if (bindingResult.hasErrors()) {
            List<AdminMeetingRoom> rooms = adminMeetingRoomService.getAllMeetingRooms();
            model.addAttribute("rooms", rooms);
            return "admin-meeting-room";
        }
        
        try {
            adminMeetingRoomService.addMeetingRoom(adminMeetingRoom);
            redirectAttributes.addFlashAttribute("successMessage", "Meeting room added successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("adminMeetingRoom", adminMeetingRoom);
        }
        
        return "redirect:/admin/meeting-rooms";
    }
}