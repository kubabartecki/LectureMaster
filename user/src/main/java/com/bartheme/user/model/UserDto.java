package com.bartheme.user.model;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
