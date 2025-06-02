package com.pgbd.dadinhoapi.service;

import com.pgbd.dadinhoapi.dto.UserResponseDTO;
import com.pgbd.dadinhoapi.model.Class;
import com.pgbd.dadinhoapi.model.User;
import com.pgbd.dadinhoapi.model.UserRole;
import com.pgbd.dadinhoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<UserResponseDTO> findByRole(UserRole role) {
        List<User> users = userRepository.findAllByRole(role);
        List<UserResponseDTO> dtos = new ArrayList<>();

        for (User user : users) {
            Class clas = user.getClas();
            if (clas == null) clas = new Class();
            UserResponseDTO dto = new UserResponseDTO(
                    user,
                    clas.getName(),
                    clas.getGrade(),
                    clas.getTeacher()
            );
            dtos.add(dto);
        }

        return dtos;
    }

    public UserResponseDTO findById(Long id) {
        Optional<User> optional = userRepository.findById(id);

        if (optional.isEmpty()) {
            return null;
        }

        User user = optional.get();
        Class clas = user.getClas();
        if (clas == null) clas = new Class();
        return new UserResponseDTO(
                user,
                clas.getName(),
                clas.getGrade(),
                clas.getTeacher()
        );
    }
}
