package org.example.signlinkuagecapstone.dto;

import java.util.List;

public class QuizResponse {
    private Long id;
    private String title;
    private String description;
    private Long moduleId;
    private Integer passingScore;
    private Integer questionCount;
    private List<QuizQuestionResponse> questions;

    public QuizResponse(Long id, String title, String description, Long moduleId,
                        Integer passingScore, Integer questionCount,
                        List<QuizQuestionResponse> questions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.moduleId = moduleId;
        this.passingScore = passingScore;
        this.questionCount = questionCount;
        this.questions = questions;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Long getModuleId() { return moduleId; }
    public Integer getPassingScore() { return passingScore; }
    public Integer getQuestionCount() { return questionCount; }
    public List<QuizQuestionResponse> getQuestions() { return questions; }
}

