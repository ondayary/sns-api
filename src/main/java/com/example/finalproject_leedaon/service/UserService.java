package com.example.finalproject_leedaon.service;

import com.example.finalproject_leedaon.domain.dto.UserDto;
import com.example.finalproject_leedaon.domain.entity.User;
import com.example.finalproject_leedaon.domain.dto.UserJoinRequest;
import com.example.finalproject_leedaon.exception.AppException;
import com.example.finalproject_leedaon.exception.ErrorCode;
import com.example.finalproject_leedaon.repository.UserRepository;
import com.example.finalproject_leedaon.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
        User savedUser = userRepository.save(userJoinRequest.toEntity(encoder.encode(userJoinRequest.getPassword())));
        return UserDto.builder()
                .id(savedUser.getId())
                .userName(savedUser.getUserName())
                .build();
    }

    public String login(String userName, String password) {

        // userName 없는 경우
        // 없으면 USERNAME_NOT_FOUND 발생
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND,
                        String.format("%s는 가입한 적이 없습니다.", userName)));

        // password 틀린 경우
        if(!encoder.matches(user.getPassword(), password)){
            throw new AppException(ErrorCode.INVALID_PASSWORD,
                    String.format("틀린 패스워드입니다."));
        }
        return "token 리턴";
    }
}
