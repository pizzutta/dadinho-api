package com.pgbd.dadinhoapi.validator;

import com.pgbd.dadinhoapi.annotation.ValidUserRole;
import com.pgbd.dadinhoapi.model.User;
import com.pgbd.dadinhoapi.model.UserRole;
import com.pgbd.dadinhoapi.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UserRoleValidator implements ConstraintValidator<ValidUserRole, Object> {

    @Autowired
    private UserRepository userRepository;

    private UserRole requiredRole;

    @Override
    public void initialize(ValidUserRole constraintAnnotation) {
        requiredRole = constraintAnnotation.role();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;

        List<Long> ids = new ArrayList<>();

        if (value instanceof Long id) {
            ids.add(id);
        } else if (value instanceof Collection<?> collection) {
            for (Object item : collection) {
                if (item instanceof Long id) {
                    ids.add(id);
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }

        if (ids.isEmpty()) return true;

        List<User> users = userRepository.findAllById(ids);

        if (users.size() != ids.size()) return false;

        return users.stream().allMatch(user -> user.getRole() == requiredRole);
    }
}

