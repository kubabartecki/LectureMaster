package com.bartheme.quiz.controller;

import com.bartheme.quiz.model.QuestionLatent;
import com.bartheme.quiz.model.QuestionResponse;
import com.bartheme.quiz.model.QuizDto;
import com.bartheme.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.createQuiz(quizDto.getTitle(), quizDto.getCategoryName(), quizDto.getNumQuestions());
    }

    @GetMapping("{id}")
    public ResponseEntity<List<QuestionLatent>> getQuizQuestions(@PathVariable Integer id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit")
    public ResponseEntity<Integer> scoreQuiz(@RequestBody List<QuestionResponse> responses) {
        return quizService.scoreQuiz(responses);
    }
}
