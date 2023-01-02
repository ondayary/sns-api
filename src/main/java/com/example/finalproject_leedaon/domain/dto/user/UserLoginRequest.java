package com.example.finalproject_leedaon.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginRequest { // 요청 DTO
    private String userName;
    private String password;
}
