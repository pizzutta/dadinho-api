package com.pgbd.dadinhoapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(OPTIONS).permitAll()
                                .requestMatchers(POST, "/auth/login", "/auth/register").permitAll()

                                .requestMatchers(POST, "/basket", "/item", "/item-recipe", "/level").hasRole("ADMIN")
                                .requestMatchers(GET, "/basket", "/item", "/item-recipe", "/level", "/user").hasRole("ADMIN")
                                .requestMatchers(PUT, "/basket", "/item", "/item-recipe", "/level").hasRole("ADMIN")
                                .requestMatchers(DELETE, "/basket", "/item", "/item-recipe", "/level", "/user").hasRole("ADMIN")

                                .requestMatchers(GET, "/class").hasAnyRole("ADMIN", "TEACHER")
                                .requestMatchers(POST, "/class").hasAnyRole("ADMIN", "TEACHER")
                                .requestMatchers(PUT, "/class").hasAnyRole("ADMIN", "TEACHER")
                                .requestMatchers(DELETE, "/class").hasAnyRole("ADMIN", "TEACHER")
                                .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
