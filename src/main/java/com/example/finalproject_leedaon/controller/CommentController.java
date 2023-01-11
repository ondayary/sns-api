package com.example.finalproject_leedaon.controller;

import com.example.finalproject_leedaon.domain.Response;
import com.example.finalproject_leedaon.domain.dto.comment.*;
import com.example.finalproject_leedaon.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "Comment API")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글작성
     * POST /posts/{postsId}/comments
     */
    @PostMapping("/posts/{postId}/comments")
    @ApiOperation(value = "댓글 작성", notes = "특정 게시물에 댓글을 작성합니다. 로그인한 유저만 작성이 가능합니다.")
    public Response<CommentResponse> commentCreate(@ApiParam("게시물 번호") @PathVariable Integer postId, Authentication authentication, @RequestBody CommentCreateRequest commentCreateRequest) {
        CommentDto commentDto = commentService.commentCreate(postId, authentication.getName(), commentCreateRequest);

        // comment가 어떤 내용으로 담기는지 찍어보기
        log.info("comment:{}", commentDto.getComment());

        return Response.success(commentDto.toResponse());
    }

    /**
     * 댓글 조회
     * GET /posts/{postId}/comments[?page=0]
     */
    @GetMapping("/posts/{postId}/comments")
    @ApiOperation(value = "댓글 조회", notes = "특정 게시물의 댓글 목록을 페이징 형식으로 출력합니다.")
    public Response<Page<CommentResponse>> commentList(@ApiParam("게시물 번호") @PathVariable Integer postId, @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CommentResponse> commentResponsePage = commentService.commentList(postId, pageable);
        return Response.success(commentResponsePage);
    }

    /**
     * 댓글 수정
     * PUT /posts/{postId}/comments/{id}
     * 수정시에도 authentication 필요
     */
    @PutMapping("/posts/{postId}/comments/{id}")
    @ApiOperation(value = "댓글 수정", notes = "특정 게시물의 댓글을 수정합니다. 댓글의 작성자만 수정이 가능합니다.")
    public Response<CommentResponse> commentUpdate(@ApiParam("게시물 번호") @PathVariable Integer postId, @ApiParam("댓글 번호") @PathVariable Integer id, @RequestBody CommentUpdateRequest commentUpdateRequest, Authentication authentication) {
        CommentDto commentDto = commentService.commentUpdate(postId, id, commentUpdateRequest, authentication.getName());
        return Response.success(commentDto.toResponse());
    }

    /** 댓글 삭제
     * DELETE /posts/{postsId}/comments/{id}
     * 삭제시에도 authentication 필요
     */
    @DeleteMapping("/posts/{postId}/comments/{id}")
    @ApiOperation(value = "댓글 삭제", notes = "특정 게시물의 댓글을 삭제합니다. 댓글의 작성자만 삭제가 가능합니다.")
    public Response<CommentDeleteResponse> commentDelete(@ApiParam("게시물 번호") @PathVariable Integer postId, @ApiParam("댓글 번호") @PathVariable Integer id, Authentication authentication) {
        Integer commentDelete  = commentService.commentDelete(postId, id, authentication.getName());
        return Response.success(new CommentDeleteResponse("댓글 삭제 완료", commentDelete));
    }
}