package com.bartheme.quiz.service.kafka;

import com.bartheme.quiz.model.QuizResultNotification;


public interface QuizResultPublisherService {
    void publish(QuizResultNotification quizResultNotification);
}
