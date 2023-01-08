package com.example.finalproject_leedaon.domain.dto.comment;

import com.example.finalproject_leedaon.domain.entity.Post;
import com.example.finalproject_leedaon.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Integer id;
    private String comment;
    private User user;
    private Post post;
    private LocalDateTime createdAt;

    public CommentResponse toResponse() {
        return CommentResponse.builder()
                .id(this.id)
                .comment(this.comment)
                .userName(this.user.getUserName())
                .postId(this.post.getId())
                .createdAt(this.createdAt)
                .build();
    }
}