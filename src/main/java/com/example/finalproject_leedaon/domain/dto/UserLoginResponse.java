package com.example.finalproject_leedaon.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginResponse { // 응답 DTO로 로그인 성공하면 토큰 반환
    private String token;
}
