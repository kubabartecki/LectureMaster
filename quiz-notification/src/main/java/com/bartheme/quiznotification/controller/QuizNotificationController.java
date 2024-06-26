package com.bartheme.quiznotification.controller;

import com.bartheme.quiznotification.model.QuizNotification;
import com.bartheme.quiznotification.service.kafka.QuizNotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizNotificationController {
    private final QuizNotificationService quizNotificationService;

    public QuizNotificationController(QuizNotificationService quizNotificationService) {
        this.quizNotificationService = quizNotificationService;
    }

    @GetMapping(path="/notifications")
    public Page<QuizNotification> listNotifications(final Pageable pagable) {
        return quizNotificationService.listNotifications(pagable);
    }
}
