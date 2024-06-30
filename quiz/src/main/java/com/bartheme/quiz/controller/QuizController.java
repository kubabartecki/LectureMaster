package com.bartheme.quiz.controller;

import com.bartheme.quiz.model.*;
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
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizCreateDto quizCreateDto) {
        return quizService.createQuiz(quizCreateDto.getTitle(), quizCreateDto.getCategoryName(), quizCreateDto.getNumQuestions());
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

    @PreAuthorize("hasAuthority('student:read')")
    @GetMapping("all")
    public ResponseEntity<List<QuizDto>> getAllQuizDto(){
        List<QuizDto> quizDtoList = quizService.getAllQuizDto();
        return ResponseEntity.ok().body(quizDtoList);
    }

    @PreAuthorize("hasAuthority('lecture:write')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Integer id){
        quizService.deleteQuiz(id);
        return ResponseEntity.ok("Quiz deleted");
    }
}
