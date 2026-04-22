package org.example.signlinkuagecapstone.controller;

import jakarta.validation.Valid;
import org.example.signlinkuagecapstone.dto.LessonResponse;
import org.example.signlinkuagecapstone.dto.ModuleRequest;
import org.example.signlinkuagecapstone.dto.ModuleResponse;
import org.example.signlinkuagecapstone.dto.LessonCreateRequest;
import org.example.signlinkuagecapstone.dto.QuizCreateRequest;
import org.example.signlinkuagecapstone.dto.QuizCreateResponse;
import org.example.signlinkuagecapstone.service.ModuleService;
import org.example.signlinkuagecapstone.service.LessonService;
import org.example.signlinkuagecapstone.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ModuleService moduleService;
    private final LessonService lessonService;
    private final QuizService quizService;

    public AdminController(ModuleService moduleService,
                           LessonService lessonService,
                           QuizService quizService) {
        this.moduleService = moduleService;
        this.lessonService = lessonService;
        this.quizService = quizService;
    }

    @PostMapping("/modules")
    public ResponseEntity<ModuleResponse> createModule(@Valid @RequestBody ModuleRequest request) {
        return ResponseEntity.ok(moduleService.toModuleResponse(moduleService.createModule(request)));
    }

    @PostMapping("/lessons")
    public ResponseEntity<LessonResponse> createLesson(@Valid @RequestBody LessonCreateRequest request) {
        return ResponseEntity.ok(lessonService.toLessonResponse(lessonService.createLesson(request), null));
    }

    @PostMapping("/quizzes")
    public ResponseEntity<QuizCreateResponse> createQuiz(@Valid @RequestBody QuizCreateRequest request) {
        return ResponseEntity.ok(quizService.createQuiz(request));
    }

    @DeleteMapping("/quizzes/{quizId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long quizId) {
        quizService.deleteQuiz(quizId);
        return ResponseEntity.noContent().build();
    }
}