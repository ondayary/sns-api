package com.example.finalproject_leedaon.controller;

import com.example.finalproject_leedaon.domain.dto.UserDto;
import com.example.finalproject_leedaon.domain.dto.UserJoinRequest;
import com.example.finalproject_leedaon.exception.AppException;
import com.example.finalproject_leedaon.exception.ErrorCode;
import com.example.finalproject_leedaon.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService; // service를 흉내내는 bean

    @Autowired
    ObjectMapper objectMapper; // java Object를 json으로 만들어줌

    @Test
    @DisplayName("회원가입 성공")
    @WithMockUser
    void join_success() throws Exception {
        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .userName("onda")
                .password("1q2w3e4r")
                .build();

        when(userService.join(any())).thenReturn(mock(UserDto.class));

        // Controller만 테스트
        mockMvc.perform(post("/api/v1/users/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 실패 - userName 중복")
    @WithMockUser
    void join_fail() throws Exception {
        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .userName("onda")
                .password("1q2w3e4r")
                .build();

        when(userService.join(any())).thenThrow(new AppException(
                ErrorCode.DUPLICATED_USER_NAME, ErrorCode.DUPLICATED_USER_NAME.getMessage()
        ));

        mockMvc.perform(post("/api/v1/users/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isConflict());
    }
}