package org.example.signlinkuagecapstone.repository;

import org.example.signlinkuagecapstone.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface LessonsRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByModuleId(Long moduleId);

}

