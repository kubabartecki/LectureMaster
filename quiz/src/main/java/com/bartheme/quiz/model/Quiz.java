package com.bartheme.quiz.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Quiz {
    @Id
    @SequenceGenerator(
            name = "quiz_id_sequence",
            sequenceName = "quiz_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "quiz_id_sequence"
    )
    private Integer id;
    private String title;

    @ElementCollection
    private List<Integer> questions;
}
