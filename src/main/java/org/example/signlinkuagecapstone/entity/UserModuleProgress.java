package org.example.signlinkuagecapstone.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "user_module_progress",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "module_id"})
        }
)
public class UserModuleProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "module_id")
    private Module module;

    // Progress details
    private Integer progressPercent;     // 0–100
    private Boolean completed;

    private Integer lessonsCompleted;
    private Integer totalLessons;

    private LocalDateTime startedAt;
    private LocalDateTime lastUpdatedAt;

    public UserModuleProgress() {
        this.startedAt = LocalDateTime.now();
        this.lastUpdatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Module getModule() { return module; }
    public void setModule(Module module) { this.module = module; }

    public Integer getProgressPercent() { return progressPercent; }
    public void setProgressPercent(Integer progressPercent) {
        this.progressPercent = progressPercent;
        this.lastUpdatedAt = LocalDateTime.now();
    }

    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) {
        this.completed = completed;
        this.lastUpdatedAt = LocalDateTime.now();
    }

    public Integer getLessonsCompleted() { return lessonsCompleted; }
    public void setLessonsCompleted(Integer lessonsCompleted) {
        this.lessonsCompleted = lessonsCompleted;
        this.lastUpdatedAt = LocalDateTime.now();
    }

    public Integer getTotalLessons() { return totalLessons; }
    public void setTotalLessons(Integer totalLessons) {
        this.totalLessons = totalLessons;
    }

    public LocalDateTime getStartedAt() { return startedAt; }
    public LocalDateTime getLastUpdatedAt() { return lastUpdatedAt; }
}



