package com.rkisuru.blog.service;

import com.rkisuru.blog.entity.User;
import com.rkisuru.blog.exception.AlreadyExistsException;
import com.rkisuru.blog.repository.UserRepository;
import com.rkisuru.blog.type.RegistrationSource;
import com.rkisuru.blog.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findByUsername(String email) {
        return userRepository.findByUsername(email);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

}