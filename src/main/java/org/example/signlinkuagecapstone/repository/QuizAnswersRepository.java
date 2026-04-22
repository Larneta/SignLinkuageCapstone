package org.example.signlinkuagecapstone.repository;

import org.example.signlinkuagecapstone.entity.QuizAnswers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuizAnswersRepository extends JpaRepository<QuizAnswers, Long> {

    List<QuizAnswers> findByQuestionId(Long questionId);
}