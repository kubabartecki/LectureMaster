package com.bartheme.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionLatent {
    private Integer id;
    private String description;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
}
