package com.example.finalproject_leedaon.domain.dto.comment;

import com.example.finalproject_leedaon.domain.entity.Comment;
import com.example.finalproject_leedaon.domain.entity.Post;
import com.example.finalproject_leedaon.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentCreateRequest {
    private static String comment;

    // CommentCreateRequest를 Entity로 변환해주는 로직 필요
    public static Comment toCommentEntity(User user, Post post) {
        return Comment.builder()
                .comment(comment)
                .user(user)
                .post(post)
                .build();
    }
}