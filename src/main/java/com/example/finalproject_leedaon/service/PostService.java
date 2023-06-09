package com.example.finalproject_leedaon.service;

import com.example.finalproject_leedaon.domain.dto.post.PostDto;
import com.example.finalproject_leedaon.domain.dto.post.PostReadResponse;
import com.example.finalproject_leedaon.domain.dto.post.PostUpdateRequest;
import com.example.finalproject_leedaon.domain.entity.Post;
import com.example.finalproject_leedaon.domain.entity.User;
import com.example.finalproject_leedaon.exception.AppException;
import com.example.finalproject_leedaon.repository.PostRepository;
import com.example.finalproject_leedaon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.finalproject_leedaon.exception.ErrorCode.*;

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
                .orElseThrow(() -> new AppException(USERNAME_NOT_FOUND,
                                                    USERNAME_NOT_FOUND.getMessage()));

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
        return postRepository.findAll(pageable).map(PostReadResponse::toPostReadResponse); // PostReadResponse에 있는 toPostReeadResponse를 바로 가져다 쓰겠다.
    }

    // 포스트 상세
    public PostReadResponse postDetail(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(POST_NOT_FOUND, POST_NOT_FOUND.getMessage()));
        return PostReadResponse.toPostReadResponse(post);
    }

    // 포스트 수정
    @Transactional
    public Integer postUpdate(Integer postId, PostUpdateRequest postUpdateRequest, Authentication authentication) {
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new AppException(USERNAME_NOT_FOUND, USERNAME_NOT_FOUND.getMessage()));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(POST_NOT_FOUND, POST_NOT_FOUND.getMessage()));

        if(!post.getUser().equals(user)) throw new AppException(INVALID_PERMISSION, INVALID_PERMISSION.getMessage());

        post.update(postUpdateRequest.toPost(user));
        return post.getId();
    }

    // 포스트 삭제
    public Integer postDelete(Integer postId) {
        postRepository.deleteById(postId);
        return postId;
    }

    // 마이피드 조회
    public Page<PostReadResponse> postMyFeed(Pageable pageable, String userName) {

        // user가 없는 경우
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(USERNAME_NOT_FOUND, USERNAME_NOT_FOUND.getMessage()));

        // 로그인된 유저만의 피드목록 보는 경우
        Page<Post> postMyFeed = postRepository.findByUser(user, pageable);

        return postMyFeed.map(post -> post.toPostDto().toReadResponse());
    }
}