package com.example.meetingroom.service;

import com.example.meetingroom.model.MeetingRoomBooking;
import com.example.meetingroom.repository.MeetingRoomBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CancelMeetingRoomService {

    private final MeetingRoomBookingRepository meetingRoomBookingRepository;

    @Autowired
    public CancelMeetingRoomService(MeetingRoomBookingRepository meetingRoomBookingRepository) {
        this.meetingRoomBookingRepository = meetingRoomBookingRepository;
    }

    public boolean cancelBooking(Long bookingId) {
        Optional<MeetingRoomBooking> booking = meetingRoomBookingRepository.findById(bookingId);

        if (booking.isPresent()) {
            meetingRoomBookingRepository.deleteById(bookingId);
            return true;
        } else {
            return false;
        }
    }

    public List<MeetingRoomBooking> getAllBookings() {
        return meetingRoomBookingRepository.findAll();
    }
}
