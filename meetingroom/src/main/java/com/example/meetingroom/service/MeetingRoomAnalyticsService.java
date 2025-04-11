package com.example.meetingroom.service;

import com.example.meetingroom.model.MeetingRoomBooking;
import com.example.meetingroom.repository.MeetingRoomAnalyticsRepository;
import com.example.meetingroom.repository.MeetingRoomEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MeetingRoomAnalyticsService {

    private final MeetingRoomAnalyticsRepository analyticsRepository;
    private final MeetingRoomEntityRepository roomRepository;
    
    @Autowired
    public MeetingRoomAnalyticsService(MeetingRoomAnalyticsRepository analyticsRepository, 
                                      MeetingRoomEntityRepository roomRepository) {
        this.analyticsRepository = analyticsRepository;
        this.roomRepository = roomRepository;
    }
    
    /**
     * Get booked and not booked rooms for the current time
     * @return Map with "booked" and "notBooked" lists
     */
    public Map<String, List<String>> getBookedAndNotBookedRooms() {
        LocalDateTime now = LocalDateTime.now();
        
        // Get all rooms that are currently booked
        List<MeetingRoomBooking> currentBookings = analyticsRepository.findCurrentBookings(now);
        List<String> bookedRooms = currentBookings.stream()
                                     .map(MeetingRoomBooking::getRoomName)
                                     .distinct()
                                     .collect(Collectors.toList());
        
        // Get all room names
        List<String> allRoomNames = roomRepository.findAllRoomNames();
        
        // Calculate not-booked rooms (all rooms minus booked rooms)
        List<String> notBookedRooms = allRoomNames.stream()
                                       .filter(room -> !bookedRooms.contains(room))
                                       .collect(Collectors.toList());
        
        Map<String, List<String>> result = new HashMap<>();
        result.put("booked", bookedRooms);
        result.put("notBooked", notBookedRooms);
        
        return result;
    }
    
    /**
     * Get booked and not booked rooms for a specific time range
     * @param startDate start of time range
     * @param endDate end of time range
     * @return Map with "booked" and "notBooked" lists
     */
    public Map<String, List<String>> getBookedAndNotBookedRooms(LocalDateTime startDate, LocalDateTime endDate) {
        // Get all rooms that are booked in the time range
        List<String> bookedRooms = analyticsRepository.findBookedRoomNames(startDate, endDate);
        
        // Get all room names
        List<String> allRoomNames = roomRepository.findAllRoomNames();
        
        // Calculate not-booked rooms (all rooms minus booked rooms)
        List<String> notBookedRooms = allRoomNames.stream()
                                       .filter(room -> !bookedRooms.contains(room))
                                       .collect(Collectors.toList());
        
        Map<String, List<String>> result = new HashMap<>();
        result.put("booked", bookedRooms);
        result.put("notBooked", notBookedRooms);
        
        return result;
    }
    
    /**
     * Get top bookers with their booking counts
     * @param limit Number of top bookers to return
     * @return List of maps with booker name and count
     */
    public List<Map<String, Object>> getTopBookers(int limit) {
        List<Object[]> topBookersData = analyticsRepository.findTopNBookers(limit);
        List<Map<String, Object>> result = new ArrayList<>();
        
        for (Object[] data : topBookersData) {
            Map<String, Object> bookerInfo = new HashMap<>();
            bookerInfo.put("bookedBy", data[0]);
            bookerInfo.put("bookingCount", data[1]);
            result.add(bookerInfo);
        }
        
        return result;
    }
    
    /**
     * Get booking distribution by hour of day to identify peak hours
     * @return Map with hour and booking count
     */
    public Map<Integer, Long> getPeakHoursByDay() {
        List<Object[]> hourlyData = analyticsRepository.findBookingsByHourOfDay();
        Map<Integer, Long> hourlyDistribution = new LinkedHashMap<>();
        
        for (Object[] data : hourlyData) {
            Integer hour = ((Number) data[0]).intValue();
            Long count = ((Number) data[1]).longValue();
            hourlyDistribution.put(hour, count);
        }
        
        return hourlyDistribution;
    }
    
    /**
     * Get booking distribution by day of week
     * @return Map with day name and booking count
     */
    public Map<String, Long> getBookingsByDayOfWeek() {
        List<Object[]> dayData = analyticsRepository.findBookingsByDayOfWeek();
        Map<String, Long> dayDistribution = new LinkedHashMap<>();
        
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        
        for (Object[] data : dayData) {
            Integer dayIndex = ((Number) data[0]).intValue();
            Long count = ((Number) data[1]).longValue();
            // MySQL's DAYOFWEEK() returns 1 for Sunday, 2 for Monday, etc.
            String dayName = days[dayIndex - 1];
            dayDistribution.put(dayName, count);
        }
        
        return dayDistribution;
    }
    
    /**
     * Format the peak hours data into a more user-friendly format
     * @return List of formatted peak hour information
     */
    public List<Map<String, Object>> getFormattedPeakHours() {
        Map<Integer, Long> peakHoursData = getPeakHoursByDay();
        List<Map<String, Object>> formattedData = new ArrayList<>();
        
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        
        for (Map.Entry<Integer, Long> entry : peakHoursData.entrySet()) {
            Map<String, Object> hourData = new HashMap<>();
            int hour = entry.getKey();
            
            LocalTime startTime = LocalTime.of(hour, 0);
            LocalTime endTime = LocalTime.of(hour, 59, 59);
            
            hourData.put("timeRange", startTime.format(timeFormatter) + " - " + endTime.format(timeFormatter));
            hourData.put("bookingCount", entry.getValue());
            formattedData.add(hourData);
        }
        
        return formattedData;
    }
}