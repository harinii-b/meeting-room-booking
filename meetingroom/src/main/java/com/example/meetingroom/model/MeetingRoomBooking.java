package com.example.meetingroom.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "meeting_room_bookings")
public class MeetingRoomBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // private String roomName;
    // private LocalDateTime startTime;
    // private LocalDateTime endTime;
    // private String bookedBy;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "booked_by")
    private String bookedBy;

    public MeetingRoomBooking() {}

    public MeetingRoomBooking(String roomName, LocalDateTime startTime, LocalDateTime endTime, String bookedBy) {
        this.roomName = roomName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookedBy = bookedBy;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getRoomName() {
        return roomName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    // Optional: toString method for debugging
    @Override
    public String toString() {
        return "MeetingRoomBooking{" +
                "id=" + id +
                ", roomName='" + roomName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", bookedBy='" + bookedBy + '\'' +
                '}';
    }
}

