// // package com.example.meetingroom.service;

// // public class MeetingRoomBookingService {
    
// // }

// package com.example.meetingroom.service;

// import com.example.meetingroom.model.MeetingRoomBooking;
// import com.example.meetingroom.repository.MeetingRoomBookingRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import java.time.LocalDateTime;
// import java.util.List;

// @Service
// public class MeetingRoomBookingService {

//     @Autowired
//     private MeetingRoomBookingRepository repository;

//     public boolean isRoomAvailable(String roomName, LocalDateTime startTime, LocalDateTime endTime) {
//         List<MeetingRoomBooking> overlappingBookings = repository.findByRoomNameAndStartTimeBetween(roomName, startTime, endTime);
//         return overlappingBookings.isEmpty();
//     }

//     public MeetingRoomBooking bookRoom(String roomName, LocalDateTime startTime, LocalDateTime endTime, String bookedBy) {
//         if (isRoomAvailable(roomName, startTime, endTime)) {
//             MeetingRoomBooking booking = new MeetingRoomBooking(roomName, startTime, endTime, bookedBy);
//             return repository.save(booking);
//         }
//         return null;
//     }
// }

// package com.example.meetingroom.service;

// import com.example.meetingroom.model.MeetingRoomBooking;
// import com.example.meetingroom.repository.MeetingRoomRepository;
// import org.springframework.stereotype.Service;

// import java.time.LocalDateTime;
// import java.util.List;

// @Service
// public class MeetingRoomService {

//     private final MeetingRoomRepository meetingRoomRepository;

//     public MeetingRoomService(MeetingRoomRepository meetingRoomRepository) {
//         this.meetingRoomRepository = meetingRoomRepository;
//     }

//     public List<MeetingRoomBooking> getAllBookings() {
//         return meetingRoomRepository.findAll();
//     }

//     public boolean bookRoom(String roomName, LocalDateTime startTime, LocalDateTime endTime, String bookedBy) {
//         boolean isAvailable = meetingRoomRepository
//         .findByRoomNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(roomName, endTime, startTime)
//         .isEmpty();


//         if (isAvailable) {
//             MeetingRoomBooking booking = new MeetingRoomBooking(roomName, startTime, endTime, bookedBy);
//             meetingRoomRepository.save(booking);
//             return true;
//         }
//         return false;
//     }
// }


package com.example.meetingroom.service;

import com.example.meetingroom.model.MeetingRoomBooking;
import com.example.meetingroom.repository.MeetingRoomRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeetingRoomService {

    private final MeetingRoomRepository meetingRoomRepository;

    public MeetingRoomService(MeetingRoomRepository meetingRoomRepository) {
        this.meetingRoomRepository = meetingRoomRepository;
    }

    public List<MeetingRoomBooking> getAllBookings() {
        return meetingRoomRepository.findAll();
    }

    public boolean bookRoom(String roomName, LocalDateTime startTime, LocalDateTime endTime, String bookedBy) {
        boolean isAvailable = meetingRoomRepository
            .findUserOverlappingBookings(roomName,bookedBy, startTime, endTime)
            .isEmpty();

        if (isAvailable) {
            MeetingRoomBooking booking = new MeetingRoomBooking(roomName, startTime, endTime, bookedBy);
            meetingRoomRepository.save(booking);
            return true;
        }
        return false;
    }

    @Autowired
private HttpSession session;

public List<MeetingRoomBooking> getUserOverlappingBookings(String roomName, LocalDateTime startTime, LocalDateTime endTime) {
    String loggedInUser = (String) session.getAttribute("loggedInUser"); // Get user from session
    if (loggedInUser == null) {
        throw new RuntimeException("User is not logged in.");
    }
    return meetingRoomRepository.findUserOverlappingBookings(roomName, loggedInUser, startTime, endTime);
}

}