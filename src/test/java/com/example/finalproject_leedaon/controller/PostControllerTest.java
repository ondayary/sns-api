package com.example.finalproject_leedaon.controller;

import com.example.finalproject_leedaon.domain.dto.post.PostCreateRequest;
import com.example.finalproject_leedaon.domain.dto.post.PostDto;
import com.example.finalproject_leedaon.exception.AppException;
import com.example.finalproject_leedaon.exception.ErrorCode;
import com.example.finalproject_leedaon.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("포스트 작성 성공")
    void 포스트_작성_성공() throws Exception {

        // given
        PostCreateRequest postCreateRequest = new PostCreateRequest("title", "body");

        // when
        when(postService.postCreate(any(), any(), any()))
                .thenReturn(PostDto.builder().id(1).build());

        // then
        mockMvc.perform(post("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postCreateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.message").value("포스트가 등록되었습니다."))
                .andExpect(jsonPath("$.result.postId").exists());
    }

    @Test
    @DisplayName("포스트 작성 실패 - JWT를 Bearer Token으로 보내지 않은 경우")
    @WithAnonymousUser
    void 포스트_작성_실패_1() throws Exception {

        // given
        PostCreateRequest postCreateRequest = new PostCreateRequest("title", "body");

        // when
        when(postService.postCreate(any(), any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

        // then
        mockMvc.perform(post("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postCreateRequest)))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @DisplayName("포스트 작성 실패 - JWT가 유효하지 않은 경우")
    @WithAnonymousUser
    void 포스트_작성_실패_2() throws Exception {

        // given
        PostCreateRequest postCreateRequest = new PostCreateRequest("title", "body");

        // when
        when(postService.postCreate(any(), any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_TOKEN, ErrorCode.INVALID_TOKEN.getMessage()));

        // then
        mockMvc.perform(post("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postCreateRequest)))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}
