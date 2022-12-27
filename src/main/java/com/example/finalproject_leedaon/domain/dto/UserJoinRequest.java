package com.example.finalproject_leedaon.domain.dto;

import com.example.finalproject_leedaon.domain.entity.User;
import com.example.finalproject_leedaon.domain.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserJoinRequest {

    // 회원가입시 필요한 필드 선언
    private String userName;
    private String password;


    // Dto 오브젝트를 Entity 타입으로 변환해주는 메소드
    public User toEntity(String password) {
        return User.builder()
                .userName(this.userName)
                .password(password)
                .role(UserRole.USER)
                .build();
    }
}
