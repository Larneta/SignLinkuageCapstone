package org.example.signlinkuagecapstone.dto;

import org.example.signlinkuagecapstone.entity.QuizAnswers;

public class QuizOptionResponse {
    private Long optionId;
    private String text;

    public QuizOptionResponse(Long optionId, String text) {
        this.optionId = optionId;
        this.text = text;
    }

    public static QuizOptionResponse toQuizOptionResponse(QuizAnswers quizAnswer) {
        return new QuizOptionResponse(quizAnswer.getId(), quizAnswer.getOptionText());
    }

    public Long getOptionId() { return optionId; }
    public String getText() { return text; }
}
