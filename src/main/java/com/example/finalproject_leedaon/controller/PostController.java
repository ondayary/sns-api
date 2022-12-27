package com.example.finalproject_leedaon.controller;

import com.example.finalproject_leedaon.domain.Response;
import com.example.finalproject_leedaon.domain.dto.PostCreateRequest;
import com.example.finalproject_leedaon.domain.dto.PostCreateResponse;
import com.example.finalproject_leedaon.domain.dto.PostDto;
import com.example.finalproject_leedaon.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 포스트 작성
    @PostMapping
    public Response<PostCreateResponse> postCreate(@RequestBody PostCreateRequest postCreateRequest, Authentication authentication) {
        PostDto postDto = postService.postCreate(postCreateRequest.getTitle(), postCreateRequest.getBody(), authentication.getName());
        log.info("helloworld");
        return Response.success(new PostCreateResponse("포스트가 등록되었습니다.", postDto.getId()));
    }
}
