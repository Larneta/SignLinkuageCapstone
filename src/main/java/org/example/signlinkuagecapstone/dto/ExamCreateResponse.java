package org.example.signlinkuagecapstone.dto;

public class ExamCreateResponse {

    private Long id;
    private String title;
    private String description;
    private Long moduleId;
    private Integer totalQuestions;
    private Integer passingScore;
    private Integer timeLimit;

    public ExamCreateResponse(Long id, String title, String description, Long moduleId,
                               Integer totalQuestions, Integer passingScore, Integer timeLimit) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.moduleId = moduleId;
        this.totalQuestions = totalQuestions;
        this.passingScore = passingScore;
        this.timeLimit = timeLimit;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Long getModuleId() { return moduleId; }
    public Integer getTotalQuestions() { return totalQuestions; }
    public Integer getPassingScore() { return passingScore; }
    public Integer getTimeLimit() { return timeLimit; }
}
