package com.bartheme.quiz.model;

import lombok.Data;

@Data
public class QuizDto {
    private Integer id;
    private String title;
    private Integer questionNo;

    public QuizDto(Quiz quiz) {
        this.id = quiz.getId();
        this.title = quiz.getTitle();
        this.questionNo = quiz.getQuestionIds().size();
    }
}
