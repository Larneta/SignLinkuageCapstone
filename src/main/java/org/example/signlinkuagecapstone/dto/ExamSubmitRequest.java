package org.example.signlinkuagecapstone.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class ExamSubmitRequest {

    @NotNull(message = "examId is required")
    private Long examId;

    @NotEmpty(message = "answers are required")
    private List<ExamAnswerSubmission> answers;

    public static class ExamAnswerSubmission {
        @NotNull(message = "questionId is required")
        private Long questionId;

        @NotNull(message = "answerId is required")
        private Long answerId;

        public Long getQuestionId() { return questionId; }
        public void setQuestionId(Long questionId) { this.questionId = questionId; }

        public Long getAnswerId() { return answerId; }
        public void setAnswerId(Long answerId) { this.answerId = answerId; }
    }

    public Long getExamId() { return examId; }
    public void setExamId(Long examId) { this.examId = examId; }

    public List<ExamAnswerSubmission> getAnswers() { return answers; }
    public void setAnswers(List<ExamAnswerSubmission> answers) { this.answers = answers; }
}
