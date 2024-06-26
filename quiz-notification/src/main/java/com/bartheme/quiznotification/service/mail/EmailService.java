package com.bartheme.quiznotification.service.mail;

import com.bartheme.quiznotification.model.QuizNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(QuizNotification notification) {
        String body = "Hello " +
                notification.getToSendFirstName() +
                ",\n" +
                notification.getStudentFirstName() +
                " " +
                notification.getStudentLastName() +
                " has just solved your \"" +
                notification.getQuizTitle() +
                "\" quiz!\nAnd scored " +
                notification.getScore() +
                "/" +
                notification.getMaxPoints() +
                " points!";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notification.getToSendEmail());
        message.setSubject(notification.getEmailTitle());
        message.setText(body);
        javaMailSender.send(message);
    }
}
