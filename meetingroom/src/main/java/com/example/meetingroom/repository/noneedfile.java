// package com.example.meetingroom.repository;

// public class BookingRepository {
    
// }

// package com.example.meetingroom.repository;

// import com.example.meetingroom.model.MeetingRoomBooking;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;

// import java.time.LocalDateTime;
// import java.util.List;

// @Repository
// public interface BookingRepository extends JpaRepository<MeetingRoomBooking, Long> {
    
//     // Find all bookings for a specific room
//     List<MeetingRoomBooking> findByRoomName(String roomName);
    
//     // Find all bookings by a specific user
//     List<MeetingRoomBooking> findByBookedBy(String bookedBy);
    
//     // Find overlapping bookings for a specific room
//     @Query("SELECT b FROM MeetingRoomBooking b WHERE b.roomName = :roomName AND " +
//            "((b.startTime <= :endTime AND b.endTime >= :startTime))")
//     List<MeetingRoomBooking> findOverlappingBookings(
//             @Param("roomName") String roomName,
//             @Param("startTime") LocalDateTime startTime,
//             @Param("endTime") LocalDateTime endTime);
// }