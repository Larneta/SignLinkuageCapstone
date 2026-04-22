package org.example.signlinkuagecapstone.controller;

import jakarta.validation.Valid;
import org.example.signlinkuagecapstone.dto.LessonResponse;
import org.example.signlinkuagecapstone.entity.User;
import org.example.signlinkuagecapstone.repository.UserRepository;
import org.example.signlinkuagecapstone.service.UserLessonProgressService;

import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;

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
    private final UserRepository userRepository;

    public LessonController(
            LessonService lessonService,
            UserLessonProgressService userLessonProgressService,
            UserRepository userRepository
    ) {
        this.lessonService = lessonService;
        this.userLessonProgressService = userLessonProgressService;
        this.userRepository = userRepository;
    }


    private LessonResponse toLessonResponse(Lesson lesson, Boolean completed) {
        return lessonService.toLessonResponse(lesson, completed);
    }

    @PreAuthorize("hasRole('ADMIN')")
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
    public ResponseEntity<LessonResponse> getLesson(
            @PathVariable Long lessonId,
            Authentication authentication
    ) {
        Lesson lesson = lessonService.getLessonById(lessonId);
        Boolean completed = authentication != null
                ? userLessonProgressService.isLessonCompleted(requireUser(authentication), lessonId)
                : null;
        return ResponseEntity.ok(toLessonResponse(lesson, completed));
    }


    @GetMapping("/module/{moduleId}")
    public ResponseEntity<List<LessonResponse>> getLessonsByModule(
            @PathVariable Long moduleId,
            Authentication authentication
    ) {
        User user = authentication != null ? requireUser(authentication) : null;
        List<LessonResponse> responses = lessonService
                .getLessonsByModule(moduleId)
                .stream()
            .map(lesson -> toLessonResponse(
                lesson,
                user != null ? userLessonProgressService.isLessonCompleted(user, lesson.getId()) : null
            ))
                .toList();

        return ResponseEntity.ok(responses);
    }


        @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{lessonId}")
    public ResponseEntity<LessonResponse> updateLesson(
            @PathVariable Long lessonId,
            @Valid @RequestBody LessonCreateRequest request
    ) {
        Lesson updated = lessonService.updateLesson(lessonId, request);
        return ResponseEntity.ok(toLessonResponse(updated, null));
    }


    @PreAuthorize("hasRole('ADMIN')")
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
        User user = requireUser(authentication);

        userLessonProgressService.markLessonCompleted(user, lessonId);

        return ResponseEntity.noContent().build();
    }

    private User requireUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found: " + authentication.getName()));
    }
}
