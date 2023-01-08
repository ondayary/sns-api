package com.example.finalproject_leedaon.domain.dto.comment;

import com.example.finalproject_leedaon.domain.entity.Comment;
import com.example.finalproject_leedaon.domain.entity.Post;
import com.example.finalproject_leedaon.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentUpdateRequest {
    private static String comment;

    public Comment toCommentEntity(User user, Post post) {
        return Comment.builder()
                .comment(this.comment)
                .user(user)
                .post(post)
                .build();
    }
}