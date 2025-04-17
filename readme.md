# Meeting Room Booking System

## Overview
The Meeting Room Booking System is a web-based application designed to streamline the process of reserving meeting rooms in an organization. It provides a user-friendly interface for managing room availability, booking schedules, and user authentication.

---

## Features
### 1. **User Management**
- User registration and login functionality.
- Role-based access control (e.g., Admin and Regular Users).
- Password encryption for secure authentication.

### 2. **Meeting Room Management**
- Add, update, and delete meeting rooms (Admin only).
- View a list of all available meeting rooms.
- Search for meeting rooms based on capacity and availability.

### 3. **Booking Management**
- Book a meeting room for a specific date and time.
- View, update, or cancel bookings.
- Prevent double-booking of rooms by enforcing time-slot validation.

### 4. **Session Management**
- Persistent user sessions using Spring Session with MySQL.
- Automatic session schema initialization.

### 5. **Database Integration**
- MySQL database for storing user, room, and booking data.
- Schema auto-update using Hibernate (`spring.jpa.hibernate.ddl-auto=update`).

---

## Project Structure
### 1. **Backend**
- **Framework**: Spring Boot
- **Key Components**:
  - **Controllers**: Handle HTTP requests and responses.
  - **Services**: Business logic for managing users, rooms, and bookings.
  - **Repositories**: Interface with the database using Spring Data JPA.
  - **Entities**: Define database tables (e.g., `User`, `MeetingRoom`, `Booking`).

### 2. **Frontend**
- **Framework**: (Specify if applicable, e.g., Angular, React, or Thymeleaf)
- **Key Features**:
  - User-friendly forms for booking and managing rooms.
  - Real-time feedback on booking status.

### 3. **Database**
- **MySQL Configuration**:
  - Database Name: `meeting_room_db`
  - Tables: `users`, `meeting_rooms`, `bookings`, `SPRING_SESSION`
- **Schema Management**:
  - Auto-update schema using Hibernate.
  - Session schema initialized via `classpath:org/springframework/session/jdbc/schema-mysql.sql`.

---

## Setup Instructions
### Prerequisites
- Java 17 or higher.
- MySQL 8.0 or higher.
- Maven 3.6+.
