package org.example.signlinkuagecapstone.repository;

import org.example.signlinkuagecapstone.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByModuleId(Long moduleId);
    Optional<Exam> findByIdAndModuleId(Long examId, Long moduleId);
}
