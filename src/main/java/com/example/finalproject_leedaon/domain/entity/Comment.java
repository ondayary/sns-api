package com.example.finalproject_leedaon.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private String Comment;

    public Comment(Integer id, String comment, String userName, Integer id1, LocalDateTime createdAt) {
        super();
    }

    public Comment toEntity(Comment comment) {
        return new Comment(
                comment.getId(),
                comment.getComment(),
                comment.getUser().getUserName(),
                comment.getPost().getId(),
                comment.getCreatedAt()
        );
    }
}