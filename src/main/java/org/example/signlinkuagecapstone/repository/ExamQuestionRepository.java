package org.example.signlinkuagecapstone.repository;

import org.example.signlinkuagecapstone.entity.ExamQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Long> {
    List<ExamQuestion> findByExamIdOrderByOrderIndex(Long examId);
    List<ExamQuestion> findByExamId(Long examId);
}
