package com.bartheme.quiz.model;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
}
