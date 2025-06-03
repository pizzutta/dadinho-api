package com.pgbd.dadinhoapi.annotation;

import com.pgbd.dadinhoapi.model.UserRole;
import com.pgbd.dadinhoapi.validator.UserRoleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserRoleValidator.class)
public @interface ValidUserRole {

    String message() default "Usuário(s) inválido(s) para o papel especificado.";

    UserRole role();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

