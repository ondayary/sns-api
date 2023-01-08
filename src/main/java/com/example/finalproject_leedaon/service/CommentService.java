package com.example.finalproject_leedaon.service;

import com.example.finalproject_leedaon.domain.dto.comment.CommentCreateRequest;
import com.example.finalproject_leedaon.domain.dto.comment.CommentDto;
import com.example.finalproject_leedaon.domain.dto.comment.CommentResponse;
import com.example.finalproject_leedaon.domain.entity.Comment;
import com.example.finalproject_leedaon.domain.entity.Post;
import com.example.finalproject_leedaon.domain.entity.User;
import com.example.finalproject_leedaon.exception.AppException;
import com.example.finalproject_leedaon.repository.CommentRepository;
import com.example.finalproject_leedaon.repository.PostRepository;
import com.example.finalproject_leedaon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.finalproject_leedaon.exception.ErrorCode.POST_NOT_FOUND;
import static com.example.finalproject_leedaon.exception.ErrorCode.USERNAME_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    // 의존성 주입
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 댓글 작성
    public CommentDto commentCreate(Integer id, String userName, CommentCreateRequest commentCreateRequest) {

        // userName이 없는 경우
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(USERNAME_NOT_FOUND, USERNAME_NOT_FOUND.getMessage()));

        // post가 없는 경우
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new AppException(POST_NOT_FOUND, POST_NOT_FOUND.getMessage()));

        Comment comment = commentRepository.save(CommentCreateRequest.toCommentEntity(user, post));

        return comment.toCommentDto();
    }

    // 댓글 조회
    // Pageable을 사용하여 10개씩 최신순으로 페이징
    public Page<CommentResponse> commentList(Integer postId, Pageable pageable) {

        // post가 없는 경우
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(POST_NOT_FOUND, POST_NOT_FOUND.getMessage()));

        Page<CommentResponse> commentResponsePage = commentRepository.findByPost(post, pageable)
                .map(comment -> comment.toCommentDto().toResponse());

        return commentResponsePage;
    }
}
