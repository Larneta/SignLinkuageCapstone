package org.example.signlinkuagecapstone.controller;

import jakarta.validation.Valid;
import org.example.signlinkuagecapstone.dto.*;
import org.example.signlinkuagecapstone.service.ExamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    public ResponseEntity<ExamCreateResponse> createExam(@Valid @RequestBody ExamCreateRequest request) {
        ExamCreateResponse created = examService.createExam(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{examId}")
    public ResponseEntity<ExamResponse> getExam(@PathVariable Long examId) {
        return ResponseEntity.ok(examService.getExam(examId));
    }

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<List<ExamResponse>> getExamsByModule(@PathVariable Long moduleId) {
        return ResponseEntity.ok(examService.getExamsByModule(moduleId));
    }

    @PutMapping("/{examId}")
    public ResponseEntity<ExamResponse> updateExam(
            @PathVariable Long examId,
            @Valid @RequestBody ExamUpdateRequest request) {
        ExamResponse updated = examService.updateExam(examId, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{examId}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long examId) {
        examService.deleteExam(examId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/submit")
    public ResponseEntity<ExamSubmitResponse> submitExam(@Valid @RequestBody ExamSubmitRequest request) {
        ExamSubmitResponse result = examService.submitExam(request);
        return ResponseEntity.ok(result);
    }
}
