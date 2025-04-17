package com.example.meetingroom.repository;

import com.example.meetingroom.model.AdminMeetingRoom;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMeetingRoomRepository extends JpaRepository<AdminMeetingRoom, Long> {
    // Check if a room with the same name already exists
    boolean existsByRoomName(String roomName);
    List<AdminMeetingRoom> findAllByOrderByRoomNameAsc();

}