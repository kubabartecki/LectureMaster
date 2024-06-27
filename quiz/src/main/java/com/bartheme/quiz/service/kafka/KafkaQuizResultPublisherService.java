package com.bartheme.quiz.service.kafka;

import com.bartheme.quiz.exceptions.QuizResultNotificationPublishException;
import com.bartheme.quiz.kafka.KafkaConfigProps;
import com.bartheme.quiz.model.QuizResultNotification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class KafkaQuizResultPublisherService implements QuizResultPublisherService {
    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaConfigProps kafkaConfigProps;

    public KafkaQuizResultPublisherService(
            final ObjectMapper objectMapper,
            final KafkaTemplate<String, String> kafkaTemplate,
            final KafkaConfigProps kafkaConfigProps) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaConfigProps = kafkaConfigProps;
    }

    @Override
    public void publish(QuizResultNotification quizResultNotification) {
        try {
            final String payload = objectMapper.writeValueAsString(quizResultNotification);
            kafkaTemplate.send(kafkaConfigProps.getTopic(), payload);
            log.info("Published quiz result notification: " + quizResultNotification);
        } catch (final JsonProcessingException ex) {
            throw new QuizResultNotificationPublishException("Unable to publish quiz result", ex, quizResultNotification);
        }
    }
}
