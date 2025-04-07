package com.example.meetingroom.repository;

import com.example.meetingroom.model.MeetingRoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRoomBookingRepository extends JpaRepository<MeetingRoomBooking, Long> {
}
