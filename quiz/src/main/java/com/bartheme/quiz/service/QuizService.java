package com.bartheme.quiz.service;

import com.bartheme.quiz.feign.QuizQuestionFeign;
import com.bartheme.quiz.feign.QuizUserFeign;
import com.bartheme.quiz.model.*;
import com.bartheme.quiz.repository.QuizRepository;
import com.bartheme.quiz.service.kafka.QuizResultPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuizQuestionFeign quizQuestionFeign;
    private final QuizUserFeign quizUserFeign;
    private final QuizResultPublisherService quizResultPublisherService;

    public ResponseEntity<Quiz> createQuiz(String title, String category, int numQuestions) {
        List<Integer> questionIds = quizQuestionFeign.generateQuestionsForQuiz(category, numQuestions).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questionIds);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getPrincipal().toString();
        Integer creatorId = quizUserFeign.getUserDtoByIdOrUsername(null, username).orElseThrow().getId();
        quiz.setCreatorId(creatorId);
        Quiz savedQuiz = quizRepository.save(quiz);

        return new ResponseEntity<>(savedQuiz, HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionLatent>> getQuizQuestions(Integer id) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        if (quizOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Quiz quiz = quizOptional.get();
        return quizQuestionFeign.getQuestionsForQuiz(quiz.getQuestionIds());
    }

    public ResponseEntity<Integer> scoreQuiz(List<QuestionResponse> responses, Integer quizId) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if (quizOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Quiz quiz = quizOptional.get();
        Optional<UserDto> creatorDto = quizUserFeign.getUserDtoByIdOrUsername(quiz.getCreatorId(), null);
        ResponseEntity<Integer> scoreResponse = quizQuestionFeign.getScore(responses);
        Integer score = scoreResponse.getBody();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getPrincipal().toString();

        Optional<UserDto> userDtoOptional = quizUserFeign.getUserDtoByIdOrUsername(null, username);
        if (userDtoOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
        UserDto userDto = userDtoOptional.get();
        QuizResultNotification quizResultNotification = QuizResultNotification.builder()
                .emailTitle("LectureMaster: Someone played your quiz!")
                .quizTitle(quiz.getTitle())
                .score(score)
                .maxPoints(quiz.getQuestionIds().size())
                .studentFirstName(userDto.getFirstName())
                .studentLastName(userDto.getLastName())
                .toSendEmail(creatorDto.orElseThrow().getEmail())
                .toSendFirstName(creatorDto.orElseThrow().getFirstName())
                .sentAt(LocalDateTime.now())
                .build();
        quizResultPublisherService.publish(quizResultNotification);
        return scoreResponse;
    }
}
