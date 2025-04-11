package com.example.meetingroom.repository;

import com.example.meetingroom.model.MeetingRoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingRoomAnalyticsRepository extends JpaRepository<MeetingRoomBooking, Long> {

    // Get all room names that have bookings in a specific time range
    @Query("SELECT DISTINCT b.roomName FROM MeetingRoomBooking b WHERE b.startTime >= ?1 AND b.endTime <= ?2")
    List<String> findBookedRoomNames(LocalDateTime startDate, LocalDateTime endDate);
    
    // Get top bookers with booking count
    @Query("SELECT b.bookedBy, COUNT(b) as bookingCount FROM MeetingRoomBooking b " +
           "GROUP BY b.bookedBy ORDER BY bookingCount DESC")
    List<Object[]> findTopBookers();
    
    // Get top N bookers with booking count
    @Query(value = "SELECT booked_by, COUNT(*) as booking_count FROM meeting_room_bookings " +
                  "GROUP BY booked_by ORDER BY booking_count DESC LIMIT ?1", 
           nativeQuery = true)
    List<Object[]> findTopNBookers(int limit);
    
    // Get bookings by hour of day
    @Query(value = "SELECT HOUR(start_time) as hour, COUNT(*) as booking_count " +
                  "FROM meeting_room_bookings " +
                  "GROUP BY HOUR(start_time) " +
                  "ORDER BY HOUR(start_time)", 
           nativeQuery = true)
    List<Object[]> findBookingsByHourOfDay();
    
    // Get bookings by day of week
    @Query(value = "SELECT DAYOFWEEK(start_time) as day, COUNT(*) as booking_count " +
                  "FROM meeting_room_bookings " +
                  "GROUP BY DAYOFWEEK(start_time) " +
                  "ORDER BY DAYOFWEEK(start_time)", 
           nativeQuery = true)
    List<Object[]> findBookingsByDayOfWeek();
    
    // Get current bookings (rooms currently in use)
    @Query("SELECT b FROM MeetingRoomBooking b " +
           "WHERE ?1 BETWEEN b.startTime AND b.endTime")
    List<MeetingRoomBooking> findCurrentBookings(LocalDateTime currentTime);
}