package com.example.finalproject_leedaon.service;

import com.example.finalproject_leedaon.domain.dto.UserDto;
import com.example.finalproject_leedaon.domain.entity.User;
import com.example.finalproject_leedaon.domain.dto.UserJoinRequest;
import com.example.finalproject_leedaon.exception.AppException;
import com.example.finalproject_leedaon.exception.ErrorCode;
import com.example.finalproject_leedaon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserDto join(UserJoinRequest userJoinRequest) {

        // 회원 userName(id)이 중복된 경우 중복 check해서 예외 발생
        userRepository.findByUserName(userJoinRequest.getUserName())
                // 존재하는 user는 예외처리를 하겠다
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.DUPLICATED_USER_NAME,
                            user.getUserName() + "은 이미 존재하는 아이디입니다.");
                });

        // 정보가 중복되지 않으면 회원가입
        User savedUser = userRepository.save(userJoinRequest.toEntity());
        return UserDto.builder()
                .id(savedUser.getId())
                .userName(savedUser.getUserName())
                .build();
    }
}
