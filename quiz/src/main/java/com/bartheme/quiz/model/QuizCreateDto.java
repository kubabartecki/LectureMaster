package com.bartheme.quiz.model;

import lombok.Data;

@Data
public class QuizCreateDto {
    String categoryName;
    Integer numQuestions;
    String title;
}
