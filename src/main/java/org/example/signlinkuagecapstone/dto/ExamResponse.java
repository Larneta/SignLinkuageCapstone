package org.example.signlinkuagecapstone.dto;

import java.util.List;

public class ExamResponse {

    private Long id;
    private String title;
    private String description;
    private Long moduleId;
    private Integer totalQuestions;
    private Integer passingScore;
    private Integer timeLimit;
    private List<ExamQuestionResponse> questions;

    public ExamResponse(Long id, String title, String description, Long moduleId,
                        Integer totalQuestions, Integer passingScore, Integer timeLimit,
                        List<ExamQuestionResponse> questions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.moduleId = moduleId;
        this.totalQuestions = totalQuestions;
        this.passingScore = passingScore;
        this.timeLimit = timeLimit;
        this.questions = questions;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Long getModuleId() { return moduleId; }
    public Integer getTotalQuestions() { return totalQuestions; }
    public Integer getPassingScore() { return passingScore; }
    public Integer getTimeLimit() { return timeLimit; }
    public List<ExamQuestionResponse> getQuestions() { return questions; }
}
