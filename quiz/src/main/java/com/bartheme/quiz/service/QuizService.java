package com.bartheme.quiz.service;

import com.bartheme.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;

    public ResponseEntity<String> createQuiz(String title, String category, int numQuestions) {
        return null;
    }
}
