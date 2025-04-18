package com.example.meetingroom.controller;

import com.example.meetingroom.service.MeetingRoomAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MeetingRoomAnalyticsController {

    private final MeetingRoomAnalyticsService analyticsService;
    
    @Autowired
    public MeetingRoomAnalyticsController(MeetingRoomAnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }
    
    /**
     * View controller for the analytics dashboard
     */
    @GetMapping("/dashboard")
    public String getAnalyticsDashboard(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Model model) {
        
        // Set default date range if not provided
        if (startDate == null) {
            startDate = LocalDateTime.now().minusDays(7);
        }
        if (endDate == null) {
            endDate = LocalDateTime.now().plusDays(1);
        }
        
        // Add data to the model
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        
        // Room status
        Map<String, List<String>> roomStatus = analyticsService.getBookedAndNotBookedRooms();
        model.addAttribute("roomStatus", roomStatus);
        
        // Top bookers
        List<Map<String, Object>> topBookers = analyticsService.getTopBookers(5);
        model.addAttribute("topBookers", topBookers);
        
        // Peak hours
        List<Map<String, Object>> peakHours = analyticsService.getFormattedPeakHours();
        model.addAttribute("peakHours", peakHours);
        
        // Peak days
        Map<String, Long> peakDays = analyticsService.getBookingsByDayOfWeek();
        model.addAttribute("peakDays", peakDays);

        // Average durations
        List<Map<String, Object>> averageDurations = analyticsService.getAverageBookingDurationPerRoom();
        model.addAttribute("averageDurations", averageDurations);
        //model.addAttribute("averageDurations", analyticsService.getAverageBookingDurationPerRoom());

        
        return "admin/analytics-dashboard";
    }
    
    // The REST API endpoints (for AJAX calls if needed)
    
    @GetMapping("/api/admin/analytics/room-status")
    @ResponseBody
    public ResponseEntity<Map<String, List<String>>> getCurrentRoomStatus() {
        Map<String, List<String>> roomStatus = analyticsService.getBookedAndNotBookedRooms();
        return ResponseEntity.ok(roomStatus);
    }
    
    @GetMapping("/api/admin/analytics/room-status/period")
    @ResponseBody
    public ResponseEntity<Map<String, List<String>>> getRoomStatusForPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        Map<String, List<String>> roomStatus = analyticsService.getBookedAndNotBookedRooms(startDate, endDate);
        return ResponseEntity.ok(roomStatus);
    }
    
    @GetMapping("/api/admin/analytics/top-bookers")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getTopBookers(
            @RequestParam(defaultValue = "10") int limit) {
        
        List<Map<String, Object>> topBookers = analyticsService.getTopBookers(limit);
        return ResponseEntity.ok(topBookers);
    }
    
    @GetMapping("/api/admin/analytics/peak-hours")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getPeakHours() {
        Map<String, Object> result = new HashMap<>();
        
        // Get peak hours by hour of day
        result.put("hourlyDistribution", analyticsService.getPeakHoursByDay());
        result.put("formattedPeakHours", analyticsService.getFormattedPeakHours());
        
        // Get peak days by day of week
        result.put("dailyDistribution", analyticsService.getBookingsByDayOfWeek());
        
        return ResponseEntity.ok(result);
    }

    @GetMapping("api/admin/analytics/average-booking-duration")

        public ResponseEntity<List<Map<String, Object>>> getAverageBookingDuration() {
        return ResponseEntity.ok(analyticsService.getAverageBookingDurationPerRoom());
    }

}