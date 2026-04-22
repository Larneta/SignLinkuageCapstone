package org.example.signlinkuagecapstone.dto;

import java.util.List;

public class ModuleResponse {

    private Long id;
    private String title;
    private String description;

    private Integer totalLessons;
    private Integer completedLessons;
    private Integer progressPercent;
    private Boolean completed;

    private List<LessonResponse> lessons;

    public ModuleResponse(
            Long id,
            String title,
            String description,
            Integer totalLessons,
            Integer completedLessons,
            Integer progressPercent,
            Boolean completed,
            List<LessonResponse> lessons
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.totalLessons = totalLessons;
        this.completedLessons = completedLessons;
        this.progressPercent = progressPercent;
        this.completed = completed;
        this.lessons = lessons;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }

    public Integer getTotalLessons() { return totalLessons; }
    public Integer getCompletedLessons() { return completedLessons; }
    public Integer getProgressPercent() { return progressPercent; }
    public Boolean getCompleted() { return completed; }

    public List<LessonResponse> getLessons() { return lessons; }
}

