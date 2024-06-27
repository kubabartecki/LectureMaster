package com.bartheme.quiznotification.service;

import com.bartheme.quiznotification.model.QuizNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    QuizNotification save(QuizNotification quizNotification);

    Page<QuizNotification> listNotifications(Pageable pageable);
}
