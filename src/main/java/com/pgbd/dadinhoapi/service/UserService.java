package com.pgbd.dadinhoapi.service;

import com.pgbd.dadinhoapi.dto.UserResponseDTO;
import com.pgbd.dadinhoapi.dto.UserUpdateDTO;
import com.pgbd.dadinhoapi.model.Class;
import com.pgbd.dadinhoapi.model.User;
import com.pgbd.dadinhoapi.model.UserRole;
import com.pgbd.dadinhoapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public List<UserResponseDTO> findByRole(UserRole role) {
        List<User> users = repository.findAllByRole(role);
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
        User user = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        Class clas = user.getClas();
        if (clas == null) clas = new Class();
        return new UserResponseDTO(
                user,
                clas.getName(),
                clas.getGrade(),
                clas.getTeacher()
        );
    }

    @Transactional
    public User save(UserUpdateDTO data) {
        User user = repository.findById(data.id()).orElseThrow(EntityNotFoundException::new);

        user.setName(data.name());
        user.setEmail(data.email());
        user.setPassword(new BCryptPasswordEncoder().encode(data.password()));

        repository.save(user);

        return user;
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
