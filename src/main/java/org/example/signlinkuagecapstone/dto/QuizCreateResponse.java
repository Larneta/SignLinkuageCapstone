package org.example.signlinkuagecapstone.dto;

public class QuizCreateResponse {

    private Long id;
    private String title;
    private String description;
    private Long moduleId;
    private Integer passingScore;
    private Integer questionCount;

    public QuizCreateResponse(
            Long id,
            String title,
            String description,
            Long moduleId,
            Integer passingScore,
            Integer questionCount
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.moduleId = moduleId;
        this.passingScore = passingScore;
        this.questionCount = questionCount;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Long getModuleId() { return moduleId; }
    public Integer getPassingScore() { return passingScore; }
    public Integer getQuestionCount() { return questionCount; }
}
