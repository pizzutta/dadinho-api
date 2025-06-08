package com.pgbd.dadinhoapi.repository;

import com.pgbd.dadinhoapi.model.User;
import com.pgbd.dadinhoapi.model.UserLevelMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserLevelMetricsRepository extends JpaRepository<UserLevelMetrics, Long>,
        JpaSpecificationExecutor<UserLevelMetrics> {

    List<UserLevelMetrics> findByUser(User user);

    Optional<UserLevelMetrics> findByUser_IdAndLevel_Id(Long userId, Long levelId);
}
