package org.example.signlinkuagecapstone.dto;

public class QuestionResult {
    private Long questionId;
    private Boolean correct;

    public QuestionResult(Long questionId, Boolean correct) {
        this.questionId = questionId;
        this.correct = correct;
    }

    public Long getQuestionId() { return questionId; }
    public Boolean getCorrect() { return correct; }
}
