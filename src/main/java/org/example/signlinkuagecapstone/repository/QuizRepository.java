package org.example.signlinkuagecapstone.repository;

import org.example.signlinkuagecapstone.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    ArrayList<Quiz> findByModuleId(Long moduleId);
}
