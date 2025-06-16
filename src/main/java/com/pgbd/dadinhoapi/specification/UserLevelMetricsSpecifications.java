package com.pgbd.dadinhoapi.specification;

import com.pgbd.dadinhoapi.model.Level;
import com.pgbd.dadinhoapi.model.User;
import com.pgbd.dadinhoapi.model.UserLevelMetrics;
import com.pgbd.dadinhoapi.model.filter.UserLevelMetricsFilter;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserLevelMetricsSpecifications {

    public static Specification<UserLevelMetrics> withFilters(UserLevelMetricsFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.userId() != null) {
                Join<UserLevelMetrics, User> userJoin = root.join("user");
                predicates.add(cb.equal(userJoin.get("id"), filter.userId()));
            }

            if (filter.levelId() != null) {
                Join<UserLevelMetrics, Level> levelJoin = root.join("level");
                predicates.add(cb.equal(levelJoin.get("id"), filter.levelId()));
            }

            if (filter.concluded() != null) {
                predicates.add(cb.equal(root.get("concluded"), filter.concluded()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
