package com.example.meetingroom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "meeting_rooms")
public class AdminMeetingRoom {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Room name is required")
    private String roomName;
    
    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be positive")
    private Integer capacity;
    
    private String location;
    
    private boolean hasProjector;
    
    private boolean hasVideoConference;
    
    private String amenities;
    
    // Constructors
    public AdminMeetingRoom() {
    }
    
    public AdminMeetingRoom(String roomName, Integer capacity, String location, 
                       boolean hasProjector, boolean hasVideoConference, String amenities) {
        this.roomName = roomName;
        this.capacity = capacity;
        this.location = location;
        this.hasProjector = hasProjector;
        this.hasVideoConference = hasVideoConference;
        this.amenities = amenities;
    }
    
    // Getters and Setters
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
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public boolean isHasProjector() {
        return hasProjector;
    }
    
    public void setHasProjector(boolean hasProjector) {
        this.hasProjector = hasProjector;
    }
    
    public boolean isHasVideoConference() {
        return hasVideoConference;
    }
    
    public void setHasVideoConference(boolean hasVideoConference) {
        this.hasVideoConference = hasVideoConference;
    }
    
    public String getAmenities() {
        return amenities;
    }
    
    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }
}