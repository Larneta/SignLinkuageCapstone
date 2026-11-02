package org.example.signlinkuagecapstone.service;

import org.example.signlinkuagecapstone.dto.*;
import org.example.signlinkuagecapstone.entity.*;
import org.example.signlinkuagecapstone.entity.Module;
import org.example.signlinkuagecapstone.repository.ExamAnswerRepository;
import org.example.signlinkuagecapstone.repository.ExamQuestionRepository;
import org.example.signlinkuagecapstone.repository.ExamRepository;
import org.example.signlinkuagecapstone.repository.ModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final ExamQuestionRepository examQuestionRepository;
    private final ExamAnswerRepository examAnswerRepository;
    private final ModuleRepository moduleRepository;

    public ExamService(ExamRepository examRepository,
                       ExamQuestionRepository examQuestionRepository,
                       ExamAnswerRepository examAnswerRepository,
                       ModuleRepository moduleRepository) {
        this.examRepository = examRepository;
        this.examQuestionRepository = examQuestionRepository;
        this.examAnswerRepository = examAnswerRepository;
        this.moduleRepository = moduleRepository;
    }

    @Transactional
    public ExamCreateResponse createExam(ExamCreateRequest request) {
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new RuntimeException("Module not found: " + request.getModuleId()));

        Exam exam = new Exam();
        exam.setTitle(request.getTitle());
        exam.setDescription(request.getDescription());
        exam.setModule(module);
        exam.setTotalQuestions(request.getTotalQuestions());
        exam.setPassingScore(request.getPassingScore());
        exam.setTimeLimit(request.getTimeLimit());

        Exam savedExam = examRepository.save(exam);

        // Create questions and answers
        for (ExamCreateRequest.ExamQuestionRequest qReq : request.getQuestions()) {
            ExamQuestion question = new ExamQuestion();
            question.setQuestionText(qReq.getQuestionText());
            question.setImageUrl(qReq.getImageUrl());
            question.setOrderIndex(qReq.getOrderIndex() != null ? qReq.getOrderIndex() : 0);
            question.setExam(savedExam);

            ExamQuestion savedQuestion = examQuestionRepository.save(question);

            // Create answers
            for (ExamCreateRequest.ExamAnswerRequest aReq : qReq.getAnswers()) {
                ExamAnswer answer = new ExamAnswer();
                answer.setOptionText(aReq.getOptionText());
                answer.setImageUrl(aReq.getImageUrl());
                answer.setIsCorrect(aReq.getIsCorrect());
                answer.setOrderIndex(aReq.getOrderIndex() != null ? aReq.getOrderIndex() : 0);
                answer.setQuestion(savedQuestion);

                examAnswerRepository.save(answer);
            }
        }

        return toExamCreateResponse(savedExam);
    }

    public ExamResponse getExam(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found: " + examId));
        return toExamResponse(exam);
    }

    public List<ExamResponse> getExamsByModule(Long moduleId) {
        List<Exam> exams = examRepository.findByModuleId(moduleId);
        return exams.stream()
                .map(this::toExamResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ExamResponse updateExam(Long examId, ExamUpdateRequest request) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found: " + examId));

        exam.setTitle(request.getTitle());
        exam.setDescription(request.getDescription());
        exam.setPassingScore(request.getPassingScore());
        exam.setTimeLimit(request.getTimeLimit());

        // Delete existing questions
        examQuestionRepository.deleteAll(exam.getQuestions());

        // Create new questions
        if (request.getQuestions() != null) {
            for (ExamCreateRequest.ExamQuestionRequest qReq : request.getQuestions()) {
                ExamQuestion question = new ExamQuestion();
                question.setQuestionText(qReq.getQuestionText());
                question.setImageUrl(qReq.getImageUrl());
                question.setOrderIndex(qReq.getOrderIndex() != null ? qReq.getOrderIndex() : 0);
                question.setExam(exam);

                ExamQuestion savedQuestion = examQuestionRepository.save(question);

                for (ExamCreateRequest.ExamAnswerRequest aReq : qReq.getAnswers()) {
                    ExamAnswer answer = new ExamAnswer();
                    answer.setOptionText(aReq.getOptionText());
                    answer.setImageUrl(aReq.getImageUrl());
                    answer.setIsCorrect(aReq.getIsCorrect());
                    answer.setOrderIndex(aReq.getOrderIndex() != null ? aReq.getOrderIndex() : 0);
                    answer.setQuestion(savedQuestion);

                    examAnswerRepository.save(answer);
                }
            }
        }

        Exam updatedExam = examRepository.save(exam);
        return toExamResponse(updatedExam);
    }

    @Transactional
    public void deleteExam(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found: " + examId));
        examRepository.delete(exam);
    }

    @Transactional
    public ExamSubmitResponse submitExam(ExamSubmitRequest request) {
        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found: " + request.getExamId()));

        int correctCount = 0;
        List<ExamSubmitResponse.ExamQuestionResult> results = new ArrayList<>();

        for (ExamSubmitRequest.ExamAnswerSubmission submission : request.getAnswers()) {
            ExamQuestion question = examQuestionRepository.findById(submission.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found: " + submission.getQuestionId()));

            ExamAnswer selectedAnswer = examAnswerRepository.findById(submission.getAnswerId())
                    .orElseThrow(() -> new RuntimeException("Answer not found: " + submission.getAnswerId()));

            // Find correct answer
            ExamAnswer correctAnswer = question.getAnswers().stream()
                    .filter(ExamAnswer::getIsCorrect)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No correct answer found for question: " + question.getId()));

            boolean isCorrect = selectedAnswer.getId().equals(correctAnswer.getId());
            if (isCorrect) {
                correctCount++;
            }

            ExamSubmitResponse.ExamQuestionResult result = new ExamSubmitResponse.ExamQuestionResult(
                    question.getId(),
                    question.getQuestionText(),
                    question.getImageUrl(),
                    selectedAnswer.getId(),
                    selectedAnswer.getOptionText(),
                    selectedAnswer.getImageUrl(),
                    correctAnswer.getId(),
                    correctAnswer.getOptionText(),
                    correctAnswer.getImageUrl(),
                    isCorrect
            );
            results.add(result);
        }

        int score = (correctCount * 100) / exam.getTotalQuestions();
        boolean passed = score >= exam.getPassingScore();

        return new ExamSubmitResponse(
                exam.getId(),
                exam.getTotalQuestions(),
                correctCount,
                score,
                passed,
                results
        );
    }

    // ✅ DTO Conversion Methods
    private ExamCreateResponse toExamCreateResponse(Exam exam) {
        return new ExamCreateResponse(
                exam.getId(),
                exam.getTitle(),
                exam.getDescription(),
                exam.getModule().getId(),
                exam.getTotalQuestions(),
                exam.getPassingScore(),
                exam.getTimeLimit()
        );
    }

    private ExamResponse toExamResponse(Exam exam) {
        List<ExamQuestionResponse> questions = examQuestionRepository.findByExamIdOrderByOrderIndex(exam.getId())
                .stream()
                .map(this::toExamQuestionResponse)
                .collect(Collectors.toList());

        return new ExamResponse(
                exam.getId(),
                exam.getTitle(),
                exam.getDescription(),
                exam.getModule().getId(),
                exam.getTotalQuestions(),
                exam.getPassingScore(),
                exam.getTimeLimit(),
                questions
        );
    }

    private ExamQuestionResponse toExamQuestionResponse(ExamQuestion question) {
        List<ExamOptionResponse> answers = question.getAnswers().stream()
                .sorted((a, b) -> a.getOrderIndex().compareTo(b.getOrderIndex()))
                .map(this::toExamOptionResponse)
                .collect(Collectors.toList());

        return new ExamQuestionResponse(
                question.getId(),
                question.getQuestionText(),
                question.getImageUrl(),
                question.getOrderIndex(),
                answers
        );
    }

    private ExamOptionResponse toExamOptionResponse(ExamAnswer answer) {
        return new ExamOptionResponse(
                answer.getId(),
                answer.getOptionText(),
                answer.getImageUrl(),
                answer.getOrderIndex()
        );
    }
}
