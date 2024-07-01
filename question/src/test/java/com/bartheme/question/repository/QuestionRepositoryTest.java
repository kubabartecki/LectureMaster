package com.bartheme.question.repository;

import com.bartheme.question.model.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class QuestionRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;

    @AfterEach
    void tearDown() {
        questionRepository.deleteAll();
    }

    @Test
    public void QuestionRepository_SaveQuestion_ShouldExist() {
        String description = "Can a programmer create an original question?";
        Question question = Question.builder()
                .description(description)
                .option1("yes")
                .option2("no")
                .option3("it depends")
                .option4("...")
                .category("stand-up")
                .difficultyLevel("EASY")
                .rightAnswer("it depends")
                .build();
        questionRepository.save(question);

        boolean expected = questionRepository.existsQuestionByDescription(description);

        assertThat(expected).isTrue();
    }

    @Test
    public void QuestionRepository_ShouldNotExist() {
        String description = "Can a programmer create an original question?";

        boolean expected = questionRepository.existsQuestionByDescription(description);

        assertThat(expected).isFalse();
    }
}