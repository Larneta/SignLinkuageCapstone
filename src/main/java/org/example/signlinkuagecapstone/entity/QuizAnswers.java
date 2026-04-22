package org.example.signlinkuagecapstone.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz_answers")
public class QuizAnswers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String optionText;

    @Column(nullable = false)
    private boolean correct;

    @Column(nullable = false)
    private int orderIndex;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuizQuestions question;

    public QuizAnswers() {}

    public Long getId() { return id; }

    public String getOptionText() { return optionText; }
    public void setOptionText(String optionText) { this.optionText = optionText; }

    public boolean isCorrect() { return correct; }
    public void setCorrect(boolean correct) { this.correct = correct; }

    public int getOrderIndex() { return orderIndex; }
    public void setOrderIndex(int orderIndex) { this.orderIndex = orderIndex; }

    //  REQUIRED for QuizService
    public QuizQuestions getQuestion() { return question; }
    public void setQuestion(QuizQuestions question) { this.question = question; }
}