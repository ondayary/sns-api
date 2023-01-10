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
public class Good extends PostBase { // 좋아요 기능

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

    private LocalDateTime deletedAt;
}