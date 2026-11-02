package org.example.signlinkuagecapstone.dto;

public class ExamOptionResponse {

    private Long id;
    private String optionText;
    private String imageUrl;
    private Integer orderIndex;

    public ExamOptionResponse(Long id, String optionText, String imageUrl, Integer orderIndex) {
        this.id = id;
        this.optionText = optionText;
        this.imageUrl = imageUrl;
        this.orderIndex = orderIndex;
    }

    public Long getId() { return id; }
    public String getOptionText() { return optionText; }
    public String getImageUrl() { return imageUrl; }
    public Integer getOrderIndex() { return orderIndex; }
}
