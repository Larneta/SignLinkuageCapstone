package org.example.signlinkuagecapstone.dto;

public class DashboardResponse {

    private Long moduleId;
    private String moduleTitle;
    private String moduleDescription;

    private Integer progressPercent;
    private Integer lessonsCompleted;
    private Integer totalLessons;

    private Boolean completed;
    private String status;

    public DashboardResponse(
            Long moduleId,
            String moduleTitle,
            String moduleDescription,
            Integer progressPercent,
            Integer lessonsCompleted,
            Integer totalLessons,
            Boolean completed
    ) {
        this.moduleId = moduleId;
        this.moduleTitle = moduleTitle;
        this.moduleDescription = moduleDescription;
        this.progressPercent = progressPercent;
        this.lessonsCompleted = lessonsCompleted;
        this.totalLessons = totalLessons;
        this.completed = completed;

        // ✅ Derived field (simple + powerful)
        if (completed != null && completed) {
            this.status = "COMPLETED";
        } else if (lessonsCompleted != null && lessonsCompleted > 0) {
            this.status = "IN_PROGRESS";
        } else {
            this.status = "NOT_STARTED";
        }
    }

    public Long getModuleId() { return moduleId; }
    public String getModuleTitle() { return moduleTitle; }
    public String getModuleDescription() { return moduleDescription; }
    public Integer getProgressPercent() { return progressPercent; }
    public Integer getLessonsCompleted() { return lessonsCompleted; }
    public Integer getTotalLessons() { return totalLessons; }
    public Boolean getCompleted() { return completed; }
    public String getStatus() { return status; }
}

