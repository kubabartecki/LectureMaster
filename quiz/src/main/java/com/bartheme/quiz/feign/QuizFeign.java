package com.bartheme.quiz.feign;

import com.bartheme.quiz.model.QuestionLatent;
import com.bartheme.quiz.model.QuestionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION")
public interface QuizFeign {
    @GetMapping("api/v1/question/generate")
    public ResponseEntity<List<Integer>> generateQuestionsForQuiz
            (@RequestParam String category, @RequestParam Integer numQuestions);

    @PostMapping("api/v1/question/getQuestions")
    public ResponseEntity<List<QuestionLatent>> getQuestionsForQuiz(@RequestBody List<Integer> questionIds);

    @PostMapping("api/v1/question/score")
    public ResponseEntity<Integer> getScore(@RequestBody List<QuestionResponse> responses);
}
