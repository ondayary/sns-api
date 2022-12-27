package com.example.finalproject_leedaon.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserJoinResponse {

    // 회원가입시 응답해줄 필드 선언
    private Integer id;
    private String userName;

    @Builder
    public UserJoinResponse(Integer id, String userName) {
        this.id = id;
        this.userName = userName;
    }
}
