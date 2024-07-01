package com.bartheme.question.service;

import com.bartheme.question.model.Question;
import com.bartheme.question.model.QuestionLatent;
import com.bartheme.question.model.QuestionResponse;
import com.bartheme.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionRepository.findQuestionsByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public String addQuestion(Question question) {
        if (questionRepository.existsQuestionByDescription(question.getDescription())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Description already exists");
        }
        questionRepository.save(question);
        return "Question added";
    }

    public ResponseEntity<List<Integer>> generateQuestionsForQuiz(String category, Integer numQuestions) {
        List<Integer> questions = questionRepository.findRandomQuestionIdsByCategory(category, numQuestions);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionLatent>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionLatent> latents = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for (Integer questionId : questionIds) {
            questions.add(questionRepository.findById(questionId).get());
        }

        for (Question question : questions) {
            QuestionLatent latent = QuestionLatent
                    .builder()
                    .id(question.getId())
                    .description(question.getDescription())
                    .option1(question.getOption1())
                    .option2(question.getOption2())
                    .option3(question.getOption3())
                    .option4(question.getOption4())
                    .build();
            latents.add(latent);
        }
        return new ResponseEntity<>(latents, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<QuestionResponse> responses) {
        Integer score = 0;
        for (QuestionResponse response : responses) {
            Question question = questionRepository.findById(response.getId()).get();
            if (question.getRightAnswer().equals(response.getAnswer())) {
                ++score;
            }
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
    }

    public Set<String> getAllCategories() {
        return questionRepository.findAllCategories();
    }
}
