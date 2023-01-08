package com.example.finalproject_leedaon.controller;

import com.example.finalproject_leedaon.domain.Response;
import com.example.finalproject_leedaon.domain.dto.comment.CommentCreateRequest;
import com.example.finalproject_leedaon.domain.dto.comment.CommentDto;
import com.example.finalproject_leedaon.domain.dto.comment.CommentResponse;
import com.example.finalproject_leedaon.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("{id}/comments")
    public Response<CommentResponse> commentCreate(@PathVariable Integer id, Authentication authentication, CommentCreateRequest commentCreateRequest) {
        CommentDto commentDto = commentService.commentCreate(id, authentication.getName(), commentCreateRequest);
        return Response.success(commentDto.toResponse());
    }
}