package com.example.finalproject_leedaon.controller;

import com.example.finalproject_leedaon.domain.Response;
import com.example.finalproject_leedaon.domain.dto.post.*;
import com.example.finalproject_leedaon.service.PostService;
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
@Api(tags = "Post API")
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 포스트 작성
    @PostMapping
    @ApiOperation(value = "게시물 등록", notes = "제목과 내용의 게시물을 작성합니다.")
    public Response<PostCreateResponse> postCreate(@RequestBody PostCreateRequest postCreateRequest, Authentication authentication) {
        PostDto postDto = postService.postCreate(postCreateRequest.getTitle(), postCreateRequest.getBody(), authentication.getName());
        return Response.success(new PostCreateResponse("포스트가 등록되었습니다.", postDto.getId()));
    }

    // 포스트 리스트
    @GetMapping
    @ApiOperation(value = "게시물 전체 조회", notes = "게시물 목록을 페이징 형식으로 출력합니다.")
    public Response<Page<PostReadResponse>> postList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostReadResponse> postList = postService.postList(pageable);
        return Response.success(postList);
    }

    // 포스트 상세
    @GetMapping("/{postId}")
    @ApiOperation(value = "게시물 상세 조회", notes = "특정 게시물을 상세하게 출력합니다.")
    public Response<PostReadResponse> postDetail(@ApiParam("게시물 번호") @PathVariable Integer postId) {
        return Response.success(postService.postDetail(postId));
    }

    // 포스트 수정
    @PutMapping("/{postId}")
    @ApiOperation(value = "게시물 수정", notes = "자신의 특정 게시물을 수정합니다.")
    public Response<PostUpdateResponse> postUpdate(@ApiParam("게시물 번호") @PathVariable Integer postId, @RequestBody PostUpdateRequest postUpdateRequest, Authentication authentication) {
        Integer updatedDto = postService.postUpdate(postId, postUpdateRequest, authentication);
        return Response.success(new PostUpdateResponse("포스트가 수정되었습니다.", updatedDto));
    }

    // 포스트 삭제
    @DeleteMapping("/{postId}")
    @ApiOperation(value = "게시물 삭제", notes = "자신의 특정 게시물을 삭제합니다.")
    public Response<PostDeleteResponse> postDelete(@ApiParam("게시물 번호") @PathVariable Integer postId) {
        Integer deletedDto = postService.postDelete(postId);
        return Response.success(new PostDeleteResponse("포스트가 삭제되었습니다.", deletedDto));
    }

    /** 마이피드 조회
     * GET /posts/my
     * - 내가 작성한 글만 보이는 기능
     * - 제목, 글쓴이, 내용, 작성날짜가 표시된다.
     * - 목록 기능은 페이징 기능이 포함된다. (Pageable 사용)
     *     - 한 페이지당 default 피드 갯수는 20개이다.
     *     - 총 페이지 갯수가 표시된다.
     *     - 작성날짜 기준으로 최신순으로 sort한다.
     */
    @GetMapping("/my")
    @ApiOperation(value = "나의 게시물 조회", notes = "자신이 작성한 게시물을 조회합니다.")
    public Response<Page<PostReadResponse>> postMyFeed(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, Authentication authentication) {
        Page<PostReadResponse> postMyFeed = postService.postMyFeed(pageable, authentication.getName());
        return Response.success(postMyFeed);
    }
}
