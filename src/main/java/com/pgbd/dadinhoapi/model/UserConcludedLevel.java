package com.pgbd.dadinhoapi.model;

import jakarta.persistence.*;

@Entity(name = "tb_user_concluded_level")
public class UserConcludedLevel {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;
    @Column
    private Integer totalTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
