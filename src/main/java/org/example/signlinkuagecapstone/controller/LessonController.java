package org.example.signlinkuagecapstone.controller;

import jakarta.persistence.*;
import jakarta.validation.Valid;

import org.example.signlinkuagecapstone.dto.LessonResponse;
import org.example.signlinkuagecapstone.entity.User;
import org.example.signlinkuagecapstone.service.UserLessonProgressService;

import org.springframework.security.core.Authentication;

import org.example.signlinkuagecapstone.dto.LessonCreateRequest;
import org.example.signlinkuagecapstone.entity.Lesson;
import org.example.signlinkuagecapstone.service.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;
    private final UserLessonProgressService userLessonProgressService;

    public LessonController(
            LessonService lessonService,
            UserLessonProgressService userLessonProgressService
    ) {
        this.lessonService = lessonService;
        this.userLessonProgressService = userLessonProgressService;
    }


    private LessonResponse toLessonResponse(Lesson lesson, Boolean completed) {
        return new LessonResponse(
                lesson.getId(),
                lesson.getTitle(),
                lesson.getDescription(),
                null, // videoUrl (frontend-owned)
                null, // lessonOrder (optional, future use)
                lesson.getDurationSeconds(),
                lesson.getModule().getId(),
                completed
        );
    }

    @PostMapping
    public ResponseEntity<LessonResponse> createLesson(
            @Valid @RequestBody LessonCreateRequest request
    ) {
        Lesson created = lessonService.createLesson(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toLessonResponse(created, null));
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<LessonResponse> getLesson(@PathVariable Long lessonId) {
        Lesson lesson = lessonService.getLessonById(lessonId);
        return ResponseEntity.ok(toLessonResponse(lesson, null));
    }


    @GetMapping("/module/{moduleId}")
    public ResponseEntity<List<LessonResponse>> getLessonsByModule(
            @PathVariable Long moduleId
    ) {
        List<LessonResponse> responses = lessonService
                .getLessonsByModule(moduleId)
                .stream()
                .map(lesson -> toLessonResponse(lesson, null))
                .toList();

        return ResponseEntity.ok(responses);
    }


    @PutMapping("/{lessonId}")
    public ResponseEntity<LessonResponse> updateLesson(
            @PathVariable Long lessonId,
            @Valid @RequestBody LessonCreateRequest request
    ) {
        Lesson updated = lessonService.updateLesson(lessonId, request);
        return ResponseEntity.ok(toLessonResponse(updated, null));
    }


    @DeleteMapping("/{lessonId}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long lessonId) {
        lessonService.deleteLesson(lessonId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{lessonId}/complete")
    public ResponseEntity<Void> markLessonCompleted(
            @PathVariable Long lessonId,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        userLessonProgressService.markLessonCompleted(user, lessonId);

        return ResponseEntity.noContent().build();
    }
}
