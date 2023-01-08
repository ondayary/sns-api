package com.example.finalproject_leedaon.controller;

import com.example.finalproject_leedaon.domain.Response;
import com.example.finalproject_leedaon.domain.dto.comment.CommentCreateRequest;
import com.example.finalproject_leedaon.domain.dto.comment.CommentDto;
import com.example.finalproject_leedaon.domain.dto.comment.CommentResponse;
import com.example.finalproject_leedaon.service.CommentService;
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
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글작성
     * POST /posts/{postsId}/comments
     */
    @PostMapping("/{postId}/comments")
    public Response<CommentResponse> commentCreate(@PathVariable Integer postId, Authentication authentication, CommentCreateRequest commentCreateRequest) {
        CommentDto commentDto = commentService.commentCreate(postId, authentication.getName(), commentCreateRequest);

        // comment가 어떤 내용으로 담기는지 찍어보기
        log.info("comment:{}", commentDto.getComment());

        return Response.success(commentDto.toResponse());
    }

    /** 댓글 조회
     * GET /posts/{postId}/comments[?page=0]
     */
    @GetMapping("/{postId}/comments")
    public Response<Page<CommentResponse>> commentList(@PathVariable Integer postId, @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CommentResponse> commentResponsePage = commentService.commentList(postId, pageable);
        return Response.success(commentResponsePage);
    }
}