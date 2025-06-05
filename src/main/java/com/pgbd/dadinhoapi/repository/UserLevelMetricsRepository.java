package com.pgbd.dadinhoapi.repository;

import com.pgbd.dadinhoapi.model.UserLevelMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLevelMetricsRepository extends JpaRepository<UserLevelMetrics, Long> {

    Optional<UserLevelMetrics> findByUser_IdAndLevel_Id(Long userId, Long levelId);
}
