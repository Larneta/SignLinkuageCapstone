package org.example.signlinkuagecapstone.repository;

import org.example.signlinkuagecapstone.entity.UserLessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLessonProgressRepository
        extends JpaRepository<UserLessonProgress, Long> {

    Optional<UserLessonProgress>
    findByUserIdAndLessonId(Long userId, Long lessonId);
}
