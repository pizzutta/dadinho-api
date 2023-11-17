package com.pgbd.dadinhoapi.controller;


import com.pgbd.dadinhoapi.dto.AuthenticationDTO;
import com.pgbd.dadinhoapi.dto.LoginResponseDTO;
import com.pgbd.dadinhoapi.dto.RegisterDTO;
import com.pgbd.dadinhoapi.model.User;
import com.pgbd.dadinhoapi.repository.UserRepository;
import com.pgbd.dadinhoapi.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        User user = (User) auth.getPrincipal();
        String token = tokenService.generateToken(user);

        return ResponseEntity.ok(new LoginResponseDTO(user.getId(), user.getEmail(), user.getRole().toString(), token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = new User();
        user.setEmail(data.email());
        user.setPassword(encryptedPassword);
        user.setRole(data.role());

        this.repository.save(user);

        return ResponseEntity.ok().build();
    }
}
