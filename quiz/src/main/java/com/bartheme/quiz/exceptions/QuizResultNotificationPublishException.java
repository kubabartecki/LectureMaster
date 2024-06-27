package com.bartheme.quiz.exceptions;

import com.bartheme.quiz.model.QuizResultNotification;
import lombok.Getter;

public class QuizResultNotificationPublishException extends RuntimeException{
    @Getter
    private QuizResultNotification quizResultNotification;

    public QuizResultNotificationPublishException(QuizResultNotification quizResultNotification) {
        this.quizResultNotification = quizResultNotification;
    }

    public QuizResultNotificationPublishException(String message, final QuizResultNotification quizResultNotification) {
        super(message);
        this.quizResultNotification  = quizResultNotification;
    }

    public QuizResultNotificationPublishException(Throwable cause, final QuizResultNotification quizResultNotification) {
        super(cause);
        this.quizResultNotification  = quizResultNotification;
    }

    public QuizResultNotificationPublishException(String message, Throwable cause, final QuizResultNotification quizResultNotification) {
        super(message, cause);
        this.quizResultNotification  = quizResultNotification;
    }

    public QuizResultNotificationPublishException(String message, Throwable cause, boolean enableSuppression,
                                boolean writableStackTrace, final QuizResultNotification quizResultNotification) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.quizResultNotification  = quizResultNotification;
    }
}
