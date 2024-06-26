package com.bartheme.quiznotification.listeners;

import com.bartheme.quiznotification.model.QuizNotification;
import com.bartheme.quiznotification.service.QuizNotificationService;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Profile("production")
@Slf4j
public class QuizNotificationListener {

    private final ObjectMapper objectMapper;

    private final QuizNotificationService quizNotificationService;

    public QuizNotificationListener(
            final ObjectMapper objectMapper,
            final QuizNotificationService quizNotificationService) {
        this.objectMapper = objectMapper;
        this.quizNotificationService = quizNotificationService;
    }

    @KafkaListener(topics = "quiz-result.published")
    public String listens(final String in) {
        log.info("Received Notification: {}", in);
        try {
            final QuizNotification notification = objectMapper.readValue(in, QuizNotification.class);

            final QuizNotification savedNotification = quizNotificationService.save(notification);

            log.info("Notification '{}' persisted!", savedNotification.getTimestamp().toString());

        } catch(final JsonProcessingException ex) {
            log.error("Invalid message received: {}", in);
        }

        return in;
    }

}
