package com.bartheme.quiz.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("QUIZ")
public interface QuizFeign {

}
