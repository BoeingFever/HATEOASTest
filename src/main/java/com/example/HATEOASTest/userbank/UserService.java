package com.example.HATEOASTest.userbank;

import com.example.HATEOASTest.userbank.User;
import com.example.HATEOASTest.userbank.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username, String email) {
        if (username == null || email == null) {
            throw new IllegalArgumentException("Username and email cannot be null");
        }
        User user = new User(username, email);
        return userRepository.save(user);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}