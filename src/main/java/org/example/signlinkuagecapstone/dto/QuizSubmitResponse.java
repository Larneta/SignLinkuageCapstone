package org.example.signlinkuagecapstone.dto;

import java.util.List;

public class QuizSubmitResponse {

    private Long quizId;
    private Integer correctCount;
    private Integer totalQuestions;
    private Integer scorePercent;
    private Boolean passed;
    private List<QuestionResult> results;

    public QuizSubmitResponse(Long quizId, Integer correctCount, Integer totalQuestions,
                              Integer scorePercent, Boolean passed, List<QuestionResult> results) {
        this.quizId = quizId;
        this.correctCount = correctCount;
        this.totalQuestions = totalQuestions;
        this.scorePercent = scorePercent;
        this.passed = passed;
        this.results = results;
    }

    public Long getQuizId() { return quizId; }
    public Integer getCorrectCount() { return correctCount; }
    public Integer getTotalQuestions() { return totalQuestions; }
    public Integer getScorePercent() { return scorePercent; }
    public Boolean getPassed() { return passed; }
    public List<QuestionResult> getResults() { return results; }
}
