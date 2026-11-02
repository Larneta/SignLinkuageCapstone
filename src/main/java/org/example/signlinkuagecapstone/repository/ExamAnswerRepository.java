package org.example.signlinkuagecapstone.repository;

import org.example.signlinkuagecapstone.entity.ExamAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ExamAnswerRepository extends JpaRepository<ExamAnswer, Long> {
    List<ExamAnswer> findByQuestionIdOrderByOrderIndex(Long questionId);
    Optional<ExamAnswer> findByIdAndIsCorrect(Long answerId, Boolean isCorrect);
}
