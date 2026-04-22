package org.example.signlinkuagecapstone.repository;

import org.example.signlinkuagecapstone.entity.UserModuleProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserModuleProgressRepository
        extends JpaRepository<UserModuleProgress, Long> {

    Optional<UserModuleProgress>
    findByUserIdAndModuleId(Long userId, Long moduleId);
}
