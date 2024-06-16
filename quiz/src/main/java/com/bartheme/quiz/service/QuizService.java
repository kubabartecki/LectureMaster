package com.bartheme.quiz.service;

import com.bartheme.quiz.feign.QuizFeign;
import com.bartheme.quiz.model.QuestionLatent;
import com.bartheme.quiz.model.QuestionResponse;
import com.bartheme.quiz.model.Quiz;
import com.bartheme.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuizFeign quizFeign;

    public ResponseEntity<String> createQuiz(String title, String category, int numQuestions) {
        List<Integer> questionIds = quizFeign.generateQuestionsForQuiz(category, numQuestions).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questionIds);
        quizRepository.save(quiz);

        return new ResponseEntity<>("Quiz created", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionLatent>> getQuizQuestions(Integer id) {
        Quiz quiz = quizRepository.findById(id).get();
        return quizFeign.getQuestionsForQuiz(quiz.getQuestionIds());
    }

    public ResponseEntity<Integer> scoreQuiz(List<QuestionResponse> responses) {
        return quizFeign.getScore(responses);
    }
}
