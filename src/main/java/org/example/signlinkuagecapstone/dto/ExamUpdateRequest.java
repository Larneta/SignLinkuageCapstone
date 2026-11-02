package org.example.signlinkuagecapstone.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class ExamUpdateRequest {

    @NotBlank(message = "title is required")
    private String title;

    private String description;

    @NotNull(message = "passingScore is required")
    private Integer passingScore;

    @NotNull(message = "timeLimit is required")
    private Integer timeLimit;

    @Valid
    private List<ExamCreateRequest.ExamQuestionRequest> questions;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getPassingScore() { return passingScore; }
    public void setPassingScore(Integer passingScore) { this.passingScore = passingScore; }

    public Integer getTimeLimit() { return timeLimit; }
    public void setTimeLimit(Integer timeLimit) { this.timeLimit = timeLimit; }

    public List<ExamCreateRequest.ExamQuestionRequest> getQuestions() { return questions; }
    public void setQuestions(List<ExamCreateRequest.ExamQuestionRequest> questions) { this.questions = questions; }
}
