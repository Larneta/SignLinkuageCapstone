package org.example.signlinkuagecapstone.dto;

import org.example.signlinkuagecapstone.entity.QuizQuestions;

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

    public static QuizQuestionResponse toQuizQuestionResponse(QuizQuestions quizQuestion) {
        return new QuizQuestionResponse(quizQuestion.getId(),
                quizQuestion.getQuestionText(),
                quizQuestion.getOptions().stream().map(QuizOptionResponse::toQuizOptionResponse).toList());
    }

    public Long getQuestionId() { return questionId; }
    public String getPrompt() { return prompt; }
    public List<QuizOptionResponse> getOptions() { return options; }
}
