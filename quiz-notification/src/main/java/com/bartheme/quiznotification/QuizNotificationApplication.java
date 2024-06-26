package com.bartheme.quiznotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class QuizNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizNotificationApplication.class, args);
    }

}
