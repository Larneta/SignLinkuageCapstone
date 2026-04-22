package org.example.signlinkuagecapstone.entity;

import jakarta.persistence.*;


    @Entity
    @Table(name = "Lesson")
    public class Lesson {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String title;

        @Column(length = 1000)
        private String description;

        @Column
        private String videoUrl;

        @Column
        private Integer lessonOrder;

        @Column
        private Integer durationSeconds;

        @ManyToOne
        @JoinColumn(name = "module_id", nullable = false)
        private Module module;

        public Lesson() {}

        public Long getId() { return id; }

        public String getTitle() { return title; }

        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getVideoUrl() { return videoUrl; }
        public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

        public Integer getLessonOrder() { return lessonOrder; }
        public void setLessonOrder(Integer lessonOrder) { this.lessonOrder = lessonOrder; }

        public Integer getDurationSeconds() { return durationSeconds; }
        public void setDurationSeconds(Integer durationSeconds) { this.durationSeconds = durationSeconds; }

        public Module getModule() { return module; }
        public void setModule(Module module) { this.module = module; }
    }



