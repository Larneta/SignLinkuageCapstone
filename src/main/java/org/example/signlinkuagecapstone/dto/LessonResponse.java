package org.example.signlinkuagecapstone.dto;

public class LessonResponse {

    private Long id;
    private String title;
    private String description;
    private String videoUrl;
    private Integer lessonOrder;
    private Integer durationSeconds;   //
    private Long moduleId;
    private Boolean completed;

    public LessonResponse(
            Long id,
            String title,
            String description,
            String videoUrl,
            Integer lessonOrder,
            Integer durationSeconds,
            Long moduleId,
            Boolean completed
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.lessonOrder = lessonOrder;
        this.durationSeconds = durationSeconds;
        this.moduleId = moduleId;
        this.completed = completed;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getVideoUrl() { return videoUrl; }
    public Integer getLessonOrder() { return lessonOrder; }
    public Integer getDurationSeconds() { return durationSeconds; }
    public Long getModuleId() { return moduleId; }
    public Boolean getCompleted() { return completed; }
}
