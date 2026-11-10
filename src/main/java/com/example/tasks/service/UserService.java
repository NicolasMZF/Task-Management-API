package com.example.tasks.service;
import com.example.tasks.entity.User;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
