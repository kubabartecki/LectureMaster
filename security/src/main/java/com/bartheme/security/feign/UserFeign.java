package com.bartheme.security.feign;

import com.bartheme.security.model.ApplicationUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient("USER")
public interface UserFeign {
    @GetMapping("api/v1/user/get")
    Optional<ApplicationUserDto> getApplicationUserDtoByUsername(@RequestParam String username);
}
