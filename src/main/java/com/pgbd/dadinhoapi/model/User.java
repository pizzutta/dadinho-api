package com.pgbd.dadinhoapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.List.of;

@Entity(name = "tb_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private String email;
    @JsonIgnore
    @Column
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tb_user_concluded_levels",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "level_id")
    )
    private List<Level> concludedLevels = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Level> getConcludedLevels() {
        return concludedLevels;
    }

    public void setConcludedLevels(List<Level> concludedLevels) {
        this.concludedLevels = concludedLevels;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority roleAdmin = new SimpleGrantedAuthority("ROLE_ADMIN");
        SimpleGrantedAuthority roleTeacher = new SimpleGrantedAuthority("ROLE_TEACHER");
        SimpleGrantedAuthority roleStudent = new SimpleGrantedAuthority("ROLE_STUDENT");

        if (this.role == UserRole.ADMIN) return of(roleAdmin, roleTeacher, roleStudent);
        if (this.role == UserRole.TEACHER) return of(roleTeacher, roleStudent);
        return of(roleStudent);
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
