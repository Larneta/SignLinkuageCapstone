package org.example.signlinkuagecapstone.dto;

import java.util.List;

public class QuizQuestionResponse {
    private Long questionId;
    private String prompt;
    private List<QuizOptionResponse> options;

    public QuizQuestionResponse(Long questionId, String prompt, List<QuizOptionResponse> options) {
        this.questionId = questionId;
        this.prompt = prompt;
        this.options = options;
    }

    public Long getQuestionId() { return questionId; }
    public String getPrompt() { return prompt; }
    public List<QuizOptionResponse> getOptions() { return options; }
}
