package com.pgbd.dadinhoapi.model.filter;

public record UserLevelMetricsFilter(
        Long userId,
        Long levelId,
        Boolean concluded
) {
}
