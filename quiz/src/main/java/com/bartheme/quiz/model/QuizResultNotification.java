package com.bartheme.quiz.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class QuizResultNotification {
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
