package com.example.meetingroom.repository;

import com.example.meetingroom.model.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRoomEntityRepository extends JpaRepository<MeetingRoom, Long> {
    
    // Find all room names
    @Query("SELECT r.roomName FROM MeetingRoom r")
    List<String> findAllRoomNames();
}