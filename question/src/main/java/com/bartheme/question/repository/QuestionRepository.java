package com.bartheme.question.repository;

import com.bartheme.question.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findQuestionsByCategory(String category);

    @Query(value = "SELECT q.id FROM question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :count", nativeQuery = true)
    List<Integer> findRandomQuestionIdsByCategory(String category, int count);
}
