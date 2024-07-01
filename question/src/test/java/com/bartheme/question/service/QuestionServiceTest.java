package com.bartheme.question.service;

import com.bartheme.question.model.Question;
import com.bartheme.question.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        questionService = new QuestionService(questionRepository);
    }

    @Test
    void canGetAllQuestions() {
        questionService.getAllQuestions();
        verify(questionRepository).findAll();
    }

    @Test
    void canAddQuestion() {
        Question question = Question.builder()
                .description("Can a programmer create an original question?")
                .option1("yes")
                .option2("no")
                .option3("it depends")
                .option4("...")
                .category("stand-up")
                .difficultyLevel("EASY")
                .rightAnswer("it depends")
                .build();
        questionService.addQuestion(question);

        ArgumentCaptor<Question> questionArgumentCaptor = ArgumentCaptor.forClass(Question.class);

        verify(questionRepository).save(questionArgumentCaptor.capture());
        Question capturedQuestion = questionArgumentCaptor.getValue();

        assertThat(capturedQuestion).isEqualTo(question);
    }

    @Test
    void willThrowIfQuestionDescriptionIsTaken() {
        Question question = Question.builder()
                .description("Can a programmer create an original question?")
                .option1("yes")
                .option2("no")
                .option3("it depends")
                .option4("...")
                .category("stand-up")
                .difficultyLevel("EASY")
                .rightAnswer("it depends")
                .build();

        given(questionRepository.existsQuestionByDescription(question.getDescription()))
                .willReturn(true);

        assertThatThrownBy(() -> questionService.addQuestion(question))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Description already exists");

        verify(questionRepository, never()).save(any());
    }
}