package com.bartheme.quiznotification.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class QuizNotification {
    @Id
    @SequenceGenerator(
            name = "quiz_notification_id_sequence",
            sequenceName = "quiz_notification_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "quiz_notification_id_sequence"
    )
    private Integer id;
    private String emailTitle;
    private String quizTitle;
    private Integer score;
    private Integer maxPoints;
    private String studentFirstName;
    private String studentLastName;
    private String toSendEmail;
    private String toSendFirstName;
    private LocalDateTime sentAt;
}
