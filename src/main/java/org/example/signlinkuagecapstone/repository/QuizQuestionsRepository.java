package org.example.signlinkuagecapstone.repository;

import org.example.signlinkuagecapstone.entity.QuizQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionsRepository extends JpaRepository<QuizQuestions, Long> {
    List<QuizQuestions> findByQuizId(Long quizId);
}
