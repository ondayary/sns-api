package com.example.finalproject_leedaon.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UserDto {
    private Integer id;
    private String userName;
    private String password;
}
