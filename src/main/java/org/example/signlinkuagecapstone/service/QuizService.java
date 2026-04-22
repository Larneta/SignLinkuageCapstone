package org.example.signlinkuagecapstone.service;

import org.example.signlinkuagecapstone.dto.*;
import org.example.signlinkuagecapstone.entity.Module;
import org.example.signlinkuagecapstone.entity.Quiz;
import org.example.signlinkuagecapstone.entity.QuizAnswers;
import org.example.signlinkuagecapstone.entity.QuizQuestions;
import org.example.signlinkuagecapstone.repository.ModuleRepository;
import org.example.signlinkuagecapstone.repository.QuizAnswersRepository;
import org.example.signlinkuagecapstone.repository.QuizQuestionsRepository;
import org.example.signlinkuagecapstone.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final ModuleRepository moduleRepository;
    private final QuizQuestionsRepository quizQuestionsRepository;
    private final QuizAnswersRepository quizAnswersRepository;

    public QuizService(QuizRepository quizRepository,
                       ModuleRepository moduleRepository,
                       QuizQuestionsRepository quizQuestionsRepository,
                       QuizAnswersRepository quizAnswersRepository) {
        this.quizRepository = quizRepository;
        this.moduleRepository = moduleRepository;
        this.quizQuestionsRepository = quizQuestionsRepository;
        this.quizAnswersRepository = quizAnswersRepository;
    }

    @Transactional
    public QuizCreateResponse createQuiz(QuizCreateRequest request) {

        Module module = moduleRepository.findById(request.getModuleId())
            .orElseThrow(() -> new RuntimeException("Module not found: " + request.getModuleId()));

        // 1) Create & save Quiz
        Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setModule(module);
        quiz.setPassingScore(request.getPassingScore() != null ? request.getPassingScore() : 70);
        quiz.setOrderIndex(request.getOrderIndex());

        Quiz savedQuiz = quizRepository.save(quiz);

        // 2) Create & save Questions + Answers
        for (QuizCreateRequest.QuestionRequest qReq : request.getQuestions()) {

            long correctCount = qReq.getAnswers().stream()
                    .filter(QuizCreateRequest.AnswerRequest::isCorrect)
                    .count();

            if (correctCount != 1) {
                throw new RuntimeException("Each question must have exactly 1 correct answer");
            }

            QuizQuestions question = new QuizQuestions();
            question.setQuestionText(qReq.getQuestionText());
            question.setOrderIndex(qReq.getOrderIndex());
            question.setQuiz(savedQuiz);

            QuizQuestions savedQuestion = quizQuestionsRepository.save(question);

            for (QuizCreateRequest.AnswerRequest aReq : qReq.getAnswers()) {
                QuizAnswers ans = new QuizAnswers();
                ans.setOptionText(aReq.getOptionText());
                ans.setCorrect(aReq.isCorrect());
                ans.setOrderIndex(aReq.getOrderIndex());
                ans.setQuestion(savedQuestion);

                quizAnswersRepository.save(ans);
            }
        }

        return toQuizCreateResponse(savedQuiz);
    }

    public QuizResponse getQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found: " + quizId));
        return toQuizResponse(quiz);
    }

    public List<QuizQuestionResponse> getQuestionsForQuiz(Long quizId) {
        List<QuizQuestions> questions = quizQuestionsRepository.findByQuizId(quizId);
        return questions.stream()
                .map(this::toQuizQuestionResponse)
                .collect(Collectors.toList());
    }

    public List<QuizOptionResponse> getAnswersForQuestion(Long questionId) {
        List<QuizAnswers> answers = quizAnswersRepository.findByQuestionId(questionId);
        return answers.stream()
                .map(this::toQuizOptionResponse)
                .collect(Collectors.toList());
    }

    public void deleteQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found: " + quizId));
        quizRepository.delete(quiz);
    }

    // ✅ DTO Conversion Methods
    private QuizCreateResponse toQuizCreateResponse(Quiz quiz) {
        return new QuizCreateResponse(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getDescription(),
                quiz.getModule() != null ? quiz.getModule().getId() : null,
                quiz.getPassingScore(),
                quiz.getQuestions() != null ? quiz.getQuestions().size() : getQuestionsForQuiz(quiz.getId()).size()
        );
    }

    private QuizResponse toQuizResponse(Quiz quiz) {
        List<QuizQuestionResponse> questions = quiz.getId() != null
                ? getQuestionsForQuiz(quiz.getId())
                : List.of();

        return new QuizResponse(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getDescription(),
            quiz.getModule() != null ? quiz.getModule().getId() : null,
            quiz.getPassingScore(),
                questions.size(),
                questions
        );
    }

    private QuizQuestionResponse toQuizQuestionResponse(QuizQuestions question) {
        List<QuizOptionResponse> options = question.getOptions() != null
                ? question.getOptions().stream()
                    .map(this::toQuizOptionResponse)
                    .collect(Collectors.toList())
                : List.of();

        return new QuizQuestionResponse(
                question.getId(),
                question.getQuestionText(),
                options
        );
    }

    private QuizOptionResponse toQuizOptionResponse(QuizAnswers answer) {
        return new QuizOptionResponse(
                answer.getId(),
                answer.getOptionText()
        );
    }
}

