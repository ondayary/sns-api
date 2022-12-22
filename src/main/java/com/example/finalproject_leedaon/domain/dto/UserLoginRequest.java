package com.example.finalproject_leedaon.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class UserLoginRequest { // 요청 DTO
    private String userName;
    private String password;
}
