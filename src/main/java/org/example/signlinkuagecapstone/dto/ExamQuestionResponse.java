package org.example.signlinkuagecapstone.dto;

import java.util.List;

public class ExamQuestionResponse {

    private Long id;
    private String questionText;
    private String imageUrl;
    private Integer orderIndex;
    private List<ExamOptionResponse> answers;

    public ExamQuestionResponse(Long id, String questionText, String imageUrl,
                                Integer orderIndex, List<ExamOptionResponse> answers) {
        this.id = id;
        this.questionText = questionText;
        this.imageUrl = imageUrl;
        this.orderIndex = orderIndex;
        this.answers = answers;
    }

    public Long getId() { return id; }
    public String getQuestionText() { return questionText; }
    public String getImageUrl() { return imageUrl; }
    public Integer getOrderIndex() { return orderIndex; }
    public List<ExamOptionResponse> getAnswers() { return answers; }
}
