package com.example.finalproject_leedaon.service;

import com.example.finalproject_leedaon.domain.dto.comment.*;
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

import static com.example.finalproject_leedaon.exception.ErrorCode.*;

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

    // 댓글 수정
    public CommentDto commentUpdate(Integer postId, Integer id, CommentUpdateRequest commentUpdateRequest, String userName) {

        // post가 없는 경우
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(POST_NOT_FOUND, POST_NOT_FOUND.getMessage()));

        // Comment가 없는 경우
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new AppException(COMMENT_NOT_FOUND, COMMENT_NOT_FOUND.getMessage()));

        // 작성자가 일치하지 않는 경우
        if(!comment.getUser().getUserName().equals(userName)) {
            throw new AppException(INVALID_PERMISSION, INVALID_PERMISSION.getMessage());
        }

        comment.update(comment.getComment());
        Comment updatedComment = commentRepository.save(comment);
        return updatedComment.toCommentDto();
    }

    // 댓글 삭제
    public Integer commentDelete(Integer postId, Integer id, String userName) {

        // post가 없는 경우
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(POST_NOT_FOUND, POST_NOT_FOUND.getMessage()));

        // Comment가 없는 경우
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new AppException(COMMENT_NOT_FOUND, COMMENT_NOT_FOUND.getMessage()));

        // 작성자가 일치하지 않는 경우
        if(!comment.getUser().getUserName().equals(userName)) {
            throw new AppException(INVALID_PERMISSION, INVALID_PERMISSION.getMessage());
        }

        commentRepository.delete(comment);
        commentRepository.deleteById(id);
        return id;
    }
}
