package com.example.finalproject_leedaon.controller;

import com.example.finalproject_leedaon.domain.dto.UserDto;
import com.example.finalproject_leedaon.domain.dto.UserJoinRequest;
import com.example.finalproject_leedaon.domain.Response;
import com.example.finalproject_leedaon.domain.dto.UserJoinResponse;
import com.example.finalproject_leedaon.service.UserService;
import lombok.RequiredArgsConstructor;
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
        UserDto userDto = userService.join(userJoinRequest); // userJoinRequest에 있는 내용을 빼서 entity로 만든 다음에 DB랑 연결
        return Response.success(new UserJoinResponse());
    }
}

