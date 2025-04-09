package com.example.meetingroom.service;

import com.example.meetingroom.model.AdminMeetingRoom;
import com.example.meetingroom.repository.AdminMeetingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminMeetingRoomService {
    
    private final AdminMeetingRoomRepository adminMeetingRoomRepository;
    
    @Autowired
    public AdminMeetingRoomService(AdminMeetingRoomRepository adminMeetingRoomRepository) {
        this.adminMeetingRoomRepository = adminMeetingRoomRepository;
    }
    
    // Add a new meeting room
    public AdminMeetingRoom addMeetingRoom(AdminMeetingRoom adminMeetingRoom) {
        // Check if room name already exists
        if (adminMeetingRoomRepository.existsByRoomName(adminMeetingRoom.getRoomName())) {
            throw new IllegalArgumentException("A meeting room with this name already exists");
        }
        return adminMeetingRoomRepository.save(adminMeetingRoom);
    }
    
    // Get all meeting rooms
    public List<AdminMeetingRoom> getAllMeetingRooms() {
        return adminMeetingRoomRepository.findAll();
    }
}