package com.pgbd.dadinhoapi.repository;

import com.pgbd.dadinhoapi.model.User;
import com.pgbd.dadinhoapi.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByEmail(String email);

    List<User> findAllByRole(UserRole role);
}
