package com.bartheme.question.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Question {
    @Id
    @SequenceGenerator(
            name = "question_id_sequence",
            sequenceName = "question_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "question_id_sequence"
    )
    private Integer id;
    private String description;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String rightAnswer;
    private String difficultyLevel;
    private String category;
}
