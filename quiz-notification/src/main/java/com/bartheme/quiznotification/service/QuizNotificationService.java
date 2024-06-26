package com.bartheme.quiznotification.service;

import com.bartheme.quiznotification.model.QuizNotification;
import com.bartheme.quiznotification.repository.QuizNotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuizNotificationService implements NotificationService {

    private final QuizNotificationRepository quizNotificationRepository;

    public QuizNotificationService(final QuizNotificationRepository quizNotificationRepository) {
        this.quizNotificationRepository = quizNotificationRepository;
    }

    @Override
    public QuizNotification save(final QuizNotification notification) {
        return quizNotificationRepository.save(notification);
    }

    @Override
    public Page<QuizNotification> listNotifications(final Pageable pageable) {
        return quizNotificationRepository.findAll(pageable);
    }

}
