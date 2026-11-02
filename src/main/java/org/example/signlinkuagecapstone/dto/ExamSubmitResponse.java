package org.example.signlinkuagecapstone.dto;

import java.util.List;

public class ExamSubmitResponse {

    private Long examId;
    private Integer totalQuestions;
    private Integer correctAnswers;
    private Integer score;
    private Boolean passed;
    private List<ExamQuestionResult> results;

    public ExamSubmitResponse(Long examId, Integer totalQuestions, Integer correctAnswers,
                              Integer score, Boolean passed, List<ExamQuestionResult> results) {
        this.examId = examId;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.score = score;
        this.passed = passed;
        this.results = results;
    }

    public static class ExamQuestionResult {
        private Long questionId;
        private String questionText;
        private String imageUrl;
        private Long selectedAnswerId;
        private String selectedAnswerText;
        private String selectedAnswerImageUrl;
        private Long correctAnswerId;
        private String correctAnswerText;
        private String correctAnswerImageUrl;
        private Boolean isCorrect;

        public ExamQuestionResult(Long questionId, String questionText, String imageUrl,
                                  Long selectedAnswerId, String selectedAnswerText, String selectedAnswerImageUrl,
                                  Long correctAnswerId, String correctAnswerText, String correctAnswerImageUrl,
                                  Boolean isCorrect) {
            this.questionId = questionId;
            this.questionText = questionText;
            this.imageUrl = imageUrl;
            this.selectedAnswerId = selectedAnswerId;
            this.selectedAnswerText = selectedAnswerText;
            this.selectedAnswerImageUrl = selectedAnswerImageUrl;
            this.correctAnswerId = correctAnswerId;
            this.correctAnswerText = correctAnswerText;
            this.correctAnswerImageUrl = correctAnswerImageUrl;
            this.isCorrect = isCorrect;
        }

        public Long getQuestionId() { return questionId; }
        public String getQuestionText() { return questionText; }
        public String getImageUrl() { return imageUrl; }
        public Long getSelectedAnswerId() { return selectedAnswerId; }
        public String getSelectedAnswerText() { return selectedAnswerText; }
        public String getSelectedAnswerImageUrl() { return selectedAnswerImageUrl; }
        public Long getCorrectAnswerId() { return correctAnswerId; }
        public String getCorrectAnswerText() { return correctAnswerText; }
        public String getCorrectAnswerImageUrl() { return correctAnswerImageUrl; }
        public Boolean getIsCorrect() { return isCorrect; }
    }

    public Long getExamId() { return examId; }
    public Integer getTotalQuestions() { return totalQuestions; }
    public Integer getCorrectAnswers() { return correctAnswers; }
    public Integer getScore() { return score; }
    public Boolean getPassed() { return passed; }
    public List<ExamQuestionResult> getResults() { return results; }
}
