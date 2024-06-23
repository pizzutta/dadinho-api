package com.pgbd.dadinhoapi.service;

import com.pgbd.dadinhoapi.model.User;
import com.pgbd.dadinhoapi.model.UserRole;
import com.pgbd.dadinhoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findByRole(UserRole role) {
        return userRepository.findAllByRole(role);
    }
}
