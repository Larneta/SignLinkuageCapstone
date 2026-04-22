package org.example.signlinkuagecapstone.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class QuizCreateRequest {

    @NotBlank(message = "title is required")
    private String title;

    @Size(max = 1000, message = "description must be 1000 characters or less")
    private String description;

    private int orderIndex;

    @Valid
    @NotEmpty(message = "questions are required")
    private List<QuestionRequest> questions;

    public static class QuestionRequest {
        @NotBlank(message = "questionText is required")
        @Size(max = 1000)
        private String questionText;

        private int orderIndex;

        @Valid
        @NotEmpty(message = "answers are required")
        @Size(min = 2, message = "each question must have at least 2 answers")
        private List<AnswerRequest> answers;

        public String getQuestionText() { return questionText; }
        public void setQuestionText(String questionText) { this.questionText = questionText; }
        public int getOrderIndex() { return orderIndex; }
        public void setOrderIndex(int orderIndex) { this.orderIndex = orderIndex; }
        public List<AnswerRequest> getAnswers() { return answers; }
        public void setAnswers(List<AnswerRequest> answers) { this.answers = answers; }
    }

    public static class AnswerRequest {
        @NotBlank(message = "optionText is required")
        @Size(max = 1000)
        private String optionText;

        private boolean correct;
        private int orderIndex;

        public String getOptionText() { return optionText; }
        public void setOptionText(String optionText) { this.optionText = optionText; }
        public boolean isCorrect() { return correct; }
        public void setCorrect(boolean correct) { this.correct = correct; }
        public int getOrderIndex() { return orderIndex; }
        public void setOrderIndex(int orderIndex) { this.orderIndex = orderIndex; }
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getOrderIndex() { return orderIndex; }
    public void setOrderIndex(int orderIndex) { this.orderIndex = orderIndex; }
    public List<QuestionRequest> getQuestions() { return questions; }
    public void setQuestions(List<QuestionRequest> questions) { this.questions = questions; }
}
