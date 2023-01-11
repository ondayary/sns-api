package com.example.finalproject_leedaon.controller;

import com.example.finalproject_leedaon.domain.Response;
import com.example.finalproject_leedaon.domain.dto.user.UserJoinRequest;
import com.example.finalproject_leedaon.domain.dto.user.UserJoinResponse;
import com.example.finalproject_leedaon.domain.dto.user.UserLoginRequest;
import com.example.finalproject_leedaon.domain.dto.user.UserLoginResponse;
import com.example.finalproject_leedaon.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "User API")
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor // 자동으로 DI 해주는 어노테이션
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    @ApiOperation(value = "회원가입", notes = "userName과 password로 회원가입을 합니다.")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        UserJoinResponse responseDto = userService.join(userJoinRequest);
        return Response.success(responseDto);
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "userName과 password로 로그인을 한 후 토큰을 발급 받습니다.")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        UserLoginResponse responseDto = userService.login(userLoginRequest);
        return Response.success(responseDto);
    }
}

