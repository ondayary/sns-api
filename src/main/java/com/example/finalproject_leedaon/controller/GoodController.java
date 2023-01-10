package com.example.finalproject_leedaon.controller;

import com.example.finalproject_leedaon.domain.Response;
import com.example.finalproject_leedaon.service.GoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GoodController {

    private final GoodService goodService;

    /**
     * 좋아요 누루기
     * POST /posts/{postId}/likes
     */
    @PostMapping("/posts/{postId}/likes")
    public Response<String> goodPush(@PathVariable Integer postId, Authentication authentication) {
        Integer goodPush = goodService.goodPush(postId, authentication.getName());

        if(goodPush != 0) { // 좋아요를 눌렀으면
            return Response.success("좋아요를 눌렀습니다.");
        }
        return null;
    }
}