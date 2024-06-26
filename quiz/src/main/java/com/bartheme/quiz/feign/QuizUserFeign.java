package com.bartheme.quiz.feign;

import com.bartheme.quiz.model.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient("USER")
public interface QuizUserFeign {
    @GetMapping("api/v1/user/get/details")
    public Optional<UserDto> getUserDtoByIdOrUsername(@RequestParam(required = false) Integer id,
                                                      @RequestParam(required = false) String username);
}
