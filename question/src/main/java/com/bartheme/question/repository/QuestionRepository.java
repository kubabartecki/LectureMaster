package com.bartheme.question.repository;

import com.bartheme.question.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findQuestionsByCategory(String category);
}
