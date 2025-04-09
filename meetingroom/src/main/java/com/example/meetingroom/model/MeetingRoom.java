package com.example.meetingroom.model;

import jakarta.persistence.*;

@Entity
@Table(name = "meeting_rooms")
public class MeetingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "capacity")
    private Integer capacity;
    
    @Column(name = "description")
    private String description;

    // Default constructor
    public MeetingRoom() {}

    // Constructor with fields
    public MeetingRoom(String roomName, Integer capacity, String description) {
        this.roomName = roomName;
        this.capacity = capacity;
        this.description = description;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MeetingRoom{" +
                "id=" + id +
                ", roomName='" + roomName + '\'' +
                ", capacity=" + capacity +
                ", description='" + description + '\'' +
                '}';
    }
}