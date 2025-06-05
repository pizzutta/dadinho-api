package com.pgbd.dadinhoapi.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity(name = "tb_user_level_metrics")
public class UserLevelMetrics {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;
    @Column(name = "total_time")
    private Integer totalTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }
}
