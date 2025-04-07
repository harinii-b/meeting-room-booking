package com.example.meetingroom.repository;

import com.example.meetingroom.model.MeetingRoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancelMeetingRoomRepository extends JpaRepository<MeetingRoomBooking, Long> {
    void deleteById(Long id); // This will delete the booking based on booking id
}
