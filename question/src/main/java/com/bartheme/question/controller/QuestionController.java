package com.bartheme.question.controller;

import com.bartheme.question.model.Question;
import com.bartheme.question.model.QuestionLatent;
import com.bartheme.question.model.QuestionResponse;
import com.bartheme.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PreAuthorize("hasAuthority('lecture:read')")
    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @PreAuthorize("hasAuthority('lecture:read')")
    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PreAuthorize("hasAuthority('lecture:write')")
    @PostMapping
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    // for quiz microservice
    // todo ban question access in api gateway
    // is there possibility to get question bypassing api gateway? and how to protect in that situation
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> generateQuestionsForQuiz
            (@RequestParam String category, @RequestParam Integer numQuestions) {
        return questionService.generateQuestionsForQuiz(category, numQuestions);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionLatent>> getQuestionsForQuiz(@RequestBody List<Integer> questionIds) {
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("score")
    public ResponseEntity<Integer> getScore(@RequestBody List<QuestionResponse> responses) {
        return questionService.getScore(responses);
    }
}
