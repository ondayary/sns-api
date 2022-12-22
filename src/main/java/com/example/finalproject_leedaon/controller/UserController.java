package com.example.finalproject_leedaon.controller;

import com.example.finalproject_leedaon.domain.dto.*;
import com.example.finalproject_leedaon.domain.Response;
import com.example.finalproject_leedaon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor // 자동으로 DI 해주는 어노테이션
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        UserJoinResponse responseDto = userService.join(userJoinRequest);
        return Response.success(responseDto);
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        UserLoginResponse responseDto = userService.login(userLoginRequest);
        return Response.success(responseDto);
    }
}

