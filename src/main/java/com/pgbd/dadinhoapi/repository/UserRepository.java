package com.pgbd.dadinhoapi.repository;

import com.pgbd.dadinhoapi.model.User;
import com.pgbd.dadinhoapi.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

public interface UserRepository extends JpaRepository<User, Long> {

    default List<User> getReferencesByIds(List<Long> ids) {
        return ids.stream()
                .map(this::getReferenceById)
                .collect(toCollection(ArrayList::new));
    }

    UserDetails findByEmail(String email);

    List<User> findAllByRole(UserRole role);
}
