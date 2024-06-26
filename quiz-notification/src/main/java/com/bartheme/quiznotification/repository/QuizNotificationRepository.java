package com.bartheme.quiznotification.repository;

import com.bartheme.quiznotification.model.QuizNotification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuizNotificationRepository extends CrudRepository<QuizNotification, Integer>,
        PagingAndSortingRepository<QuizNotification, Integer> {
}
