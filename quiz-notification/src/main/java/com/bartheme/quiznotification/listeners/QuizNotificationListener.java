package com.bartheme.quiznotification.listeners;

import com.bartheme.quiznotification.model.QuizNotification;
import com.bartheme.quiznotification.service.kafka.QuizNotificationService;
import com.bartheme.quiznotification.service.mail.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QuizNotificationListener {

    private final ObjectMapper objectMapper;
    private final QuizNotificationService quizNotificationService;
    private final EmailService emailService;

    public QuizNotificationListener(
            final ObjectMapper objectMapper,
            final QuizNotificationService quizNotificationService,
            final EmailService emailService) {
        this.objectMapper = objectMapper;
        this.quizNotificationService = quizNotificationService;
        this.emailService = emailService;
    }

    @KafkaListener(topics = "quiz-result.published", groupId = "quiz-notification")
    public String listens(final String in) {
        log.info("Received Notification: {}", in);
        try {
            final QuizNotification notification = objectMapper.readValue(in, QuizNotification.class);
            final QuizNotification savedNotification = quizNotificationService.save(notification);
            log.info("Notification '{}' persisted!", savedNotification.getSentAt().toString());

            emailService.sendEmail(notification);
        } catch(final JsonProcessingException ex) {
            log.error("Invalid message received: {}", in);
        }

        return in;
    }

}
