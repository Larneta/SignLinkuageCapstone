package org.example.signlinkuagecapstone.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class ExamCreateRequest {

    @NotBlank(message = "title is required")
    private String title;

    private String description;

    @NotNull(message = "moduleId is required")
    private Long moduleId;

    @NotNull(message = "totalQuestions is required")
    private Integer totalQuestions;

    @NotNull(message = "passingScore is required")
    private Integer passingScore;

    @NotNull(message = "timeLimit is required")
    private Integer timeLimit;

    @Valid
    @NotEmpty(message = "questions are required")
    private List<ExamQuestionRequest> questions;

    public static class ExamQuestionRequest {
        @NotBlank(message = "questionText is required")
        private String questionText;

        @NotBlank(message = "imageUrl is required")
        private String imageUrl;

        private Integer orderIndex;

        @Valid
        @NotEmpty(message = "answers are required")
        private List<ExamAnswerRequest> answers;

        public String getQuestionText() { return questionText; }
        public void setQuestionText(String questionText) { this.questionText = questionText; }

        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

        public Integer getOrderIndex() { return orderIndex; }
        public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }

        public List<ExamAnswerRequest> getAnswers() { return answers; }
        public void setAnswers(List<ExamAnswerRequest> answers) { this.answers = answers; }
    }

    public static class ExamAnswerRequest {
        @NotBlank(message = "optionText is required")
        private String optionText;

        @NotBlank(message = "imageUrl is required")
        private String imageUrl;

        @NotNull(message = "isCorrect is required")
        private Boolean isCorrect;

        private Integer orderIndex;

        public String getOptionText() { return optionText; }
        public void setOptionText(String optionText) { this.optionText = optionText; }

        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

        public Boolean getIsCorrect() { return isCorrect; }
        public void setIsCorrect(Boolean isCorrect) { this.isCorrect = isCorrect; }

        public Integer getOrderIndex() { return orderIndex; }
        public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getModuleId() { return moduleId; }
    public void setModuleId(Long moduleId) { this.moduleId = moduleId; }

    public Integer getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(Integer totalQuestions) { this.totalQuestions = totalQuestions; }

    public Integer getPassingScore() { return passingScore; }
    public void setPassingScore(Integer passingScore) { this.passingScore = passingScore; }

    public Integer getTimeLimit() { return timeLimit; }
    public void setTimeLimit(Integer timeLimit) { this.timeLimit = timeLimit; }

    public List<ExamQuestionRequest> getQuestions() { return questions; }
    public void setQuestions(List<ExamQuestionRequest> questions) { this.questions = questions; }
}
