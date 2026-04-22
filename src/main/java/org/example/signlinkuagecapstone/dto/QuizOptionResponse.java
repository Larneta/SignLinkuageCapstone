package org.example.signlinkuagecapstone.dto;

public class QuizOptionResponse {
    private Long optionId;
    private String text;

    public QuizOptionResponse(Long optionId, String text) {
        this.optionId = optionId;
        this.text = text;
    }

    public Long getOptionId() { return optionId; }
    public String getText() { return text; }
}
