package com.example.finalproject_leedaon.controller;

import com.example.finalproject_leedaon.domain.Response;
import com.example.finalproject_leedaon.domain.dto.*;
import com.example.finalproject_leedaon.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
        return Response.success(new PostCreateResponse("포스트가 등록되었습니다.", postDto.getId()));
    }

    // 포스트 리스트
    @GetMapping
    public Response<Page<PostReadResponse>> postList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostReadResponse> postList = postService.postList(pageable);
        return Response.success(postList);
    }

    // 포스트 상세
    @GetMapping("/{postId}")
    public Response<PostReadResponse> postDetail(@PathVariable Integer postId) {
        return Response.success(postService.postDetail(postId));
    }

    // 포스트 수정
    @PutMapping("/{postId}")
    public Response<PostUpdateResponse> postUpdate(@PathVariable Integer postId, @RequestBody PostUpdateRequest postUpdateRequest, Authentication authentication) {
        Integer updatedDto = postService.postUpdate(postId, postUpdateRequest, authentication);
        return Response.success(new PostUpdateResponse("포스트가 수정되었습니다.", updatedDto));
    }
}
