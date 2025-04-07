// package com.example.meetingroom.repository;

// import com.example.meetingroom.model.MeetingRoomBooking;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import java.time.LocalDateTime;
// import java.util.List;

// @Repository
// public interface MeetingRoomRepository extends JpaRepository<MeetingRoomBooking, Long> {

//     List<MeetingRoomBooking> findByRoomNameAndStartTimeBetween(
//             String roomName, LocalDateTime startTime, LocalDateTime endTime);
// }

// package com.example.meetingroom.repository;

// import com.example.meetingroom.model.MeetingRoomBooking;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import java.time.LocalDateTime;
// import java.util.List;

// @Repository
// public interface MeetingRoomRepository extends JpaRepository<MeetingRoomBooking, Long> {

//     List<MeetingRoomBooking> findByRoomNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
//         String roomName, LocalDateTime endTime, LocalDateTime startTime);
//     }



package com.example.meetingroom.repository;

import com.example.meetingroom.model.MeetingRoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

// public interface MeetingRoomRepository extends JpaRepository<MeetingRoomBooking, Long> {

//     @Query("SELECT m FROM MeetingRoomBooking m WHERE m.roomName = :roomName " +
//            "AND (m.startTime < :endTime AND m.endTime > :startTime)")
    
//     List<MeetingRoomBooking> findOverlappingBookings(
//         @Param("roomName") String roomName,
//         @Param("startTime") LocalDateTime startTime,
//         @Param("endTime") LocalDateTime endTime
//     );
// }

public interface MeetingRoomRepository extends JpaRepository<MeetingRoomBooking, Long> {

    @Query("SELECT m FROM MeetingRoomBooking m WHERE m.roomName = :roomName " +
           "AND m.bookedBy = :bookedBy " +
           "AND (m.startTime < :endTime AND m.endTime > :startTime)")
    List<MeetingRoomBooking> findUserOverlappingBookings(
        @Param("roomName") String roomName,
        @Param("bookedBy") String bookedBy,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime

    
    );
}




