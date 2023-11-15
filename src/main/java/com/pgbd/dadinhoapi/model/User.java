package com.pgbd.dadinhoapi.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "tb_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String email;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (this.role == UserRole.ADMIN) ? List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"))
                : List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
