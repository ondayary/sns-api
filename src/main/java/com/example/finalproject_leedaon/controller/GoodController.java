package com.example.finalproject_leedaon.controller;

import com.example.finalproject_leedaon.domain.Response;
import com.example.finalproject_leedaon.service.GoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(tags = "Good API")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GoodController {

    private final GoodService goodService;

    /**
     * 좋아요 누르기
     * POST /posts/{postId}/likes
     */
    @PostMapping("/posts/{postId}/likes")
    @ApiOperation(value = "좋아요 누르기", notes = "특정 게시물에 좋아요를 누릅니다. 한번 더 호출시 좋아요가 취소됩니다.")
    public Response<String> goodPush(@ApiParam("게시물 번호") @PathVariable Integer postId, @ApiIgnore Authentication authentication) {
        goodService.goodPush(postId, authentication.getName());
        return Response.success("좋아요를 눌렀습니다.");
    }

    /** 좋아요 개수
     * GET /posts/{postsId}/likes
     */
    @GetMapping("/posts/{postId}/likes")
    @ApiOperation(value = "좋아요 개수", notes = "특정 게시물의 좋아요 개수를 출력합니다.")
    public Response<Integer> goodCount(@ApiParam("게시물 번호") @PathVariable Integer postId) {
        Integer goodCount = goodService.goodCount(postId);
        return Response.success(goodCount);
    }
}