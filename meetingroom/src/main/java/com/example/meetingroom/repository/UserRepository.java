package com.example.meetingroom.repository;

import com.example.meetingroom.model.User;
// import org.springframework.data.jpa.repository.JpaRepository;
// import java.util.Optional;

// public interface UserRepository extends JpaRepository<User, Long> {
//     Optional<User> findByUsername(String username);
// }

// package com.example.meetingroom.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username); // Find user by username
}
