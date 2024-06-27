package com.bartheme.quiz.controller;

import com.bartheme.quiz.model.QuestionLatent;
import com.bartheme.quiz.model.QuestionResponse;
import com.bartheme.quiz.model.Quiz;
import com.bartheme.quiz.model.QuizDto;
import com.bartheme.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @PreAuthorize("hasAuthority('lecture:write')")
    @PostMapping("create")
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.createQuiz(quizDto.getTitle(), quizDto.getCategoryName(), quizDto.getNumQuestions());
    }

    @PreAuthorize("hasAuthority('student:read')")
    @GetMapping("{id}")
    public ResponseEntity<List<QuestionLatent>> getQuizQuestions(@PathVariable Integer id) {
        return quizService.getQuizQuestions(id);
    }

    @PreAuthorize("hasAuthority('student:read')")
    @PostMapping("submit/{quizId}")
    public ResponseEntity<Integer> scoreQuiz(@RequestBody List<QuestionResponse> responses, @PathVariable Integer quizId) {
        return quizService.scoreQuiz(responses, quizId);
    }
}
