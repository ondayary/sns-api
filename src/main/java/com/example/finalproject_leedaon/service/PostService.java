package com.example.finalproject_leedaon.service;

import com.example.finalproject_leedaon.domain.dto.PostDto;
import com.example.finalproject_leedaon.domain.dto.PostReadResponse;
import com.example.finalproject_leedaon.domain.entity.Post;
import com.example.finalproject_leedaon.domain.entity.User;
import com.example.finalproject_leedaon.exception.AppException;
import com.example.finalproject_leedaon.exception.ErrorCode;
import com.example.finalproject_leedaon.repository.PostRepository;
import com.example.finalproject_leedaon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor // 생성자로 DI주입
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 포스트 작성 - 회원만이 글 작성 가능
    public PostDto postCreate(String title, String body, String name) {

        // userName 없는 경우
        // 없으면 USERNAME_NOT_FOUND 발생
        User user = userRepository.findByUserName(name)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND,
                                                    ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        log.info("user:"+name);
        log.info("user:"+user.toString());

        Post savedPost = postRepository.save(Post.builder()
                .title(title)
                .body(body)
                .user(user)
                .build());

        PostDto postDto = PostDto.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .body(savedPost.getBody())
                .build();

        return postDto;
    }

    // 포스트 리스트
    public Page<PostReadResponse> postList(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostReadResponse::toPostReadResponse);
    }

    // 포스트 상세
    public PostReadResponse postDetail(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        return PostReadResponse.toPostReadResponse(post);
    }
}