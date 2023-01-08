package com.example.finalproject_leedaon.domain.entity;

import com.example.finalproject_leedaon.domain.dto.comment.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends PostBase { // 댓글 기능

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // created_at과 last_modified_at은 PostBase에 구현

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String comment;

    public CommentDto toCommentDto() {
        return CommentDto.builder()
                .id(this.id)
                .comment(this.comment)
                .post(this.post)
                .user(this.user)
                .createdAt(this.getCreatedAt())
                .build();
    }

    // 댓글 수정
    public void update(String comment) {
        this.comment = comment;
    }
}