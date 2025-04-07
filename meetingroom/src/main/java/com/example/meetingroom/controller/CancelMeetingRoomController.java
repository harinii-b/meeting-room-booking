package com.example.meetingroom.controller;

import com.example.meetingroom.service.CancelMeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cancel-meeting-room")
public class CancelMeetingRoomController {

    private final CancelMeetingRoomService cancelMeetingRoomService;

    @Autowired
    public CancelMeetingRoomController(CancelMeetingRoomService cancelMeetingRoomService) {
        this.cancelMeetingRoomService = cancelMeetingRoomService;
    }

    // Show Cancel Page with list of bookings
    @GetMapping
    public String showCancelPage(Model model) {
        model.addAttribute("bookings", cancelMeetingRoomService.getAllBookings());
        return "meeting-room-cancel";
    }

    // Cancel Booking by bookingId
    @PostMapping
    public String cancelMeetingRoom(@RequestParam Long bookingId, Model model) {
        boolean isCancelled = cancelMeetingRoomService.cancelBooking(bookingId);

        if (isCancelled) {
            model.addAttribute("successMessage", "Booking cancelled successfully!");
        } else {
            model.addAttribute("errorMessage", "Booking ID not found!");
        }

        model.addAttribute("bookings", cancelMeetingRoomService.getAllBookings());
        return "meeting-room-cancel";
    }
}
