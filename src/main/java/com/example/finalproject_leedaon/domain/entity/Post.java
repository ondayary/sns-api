package com.example.finalproject_leedaon.domain.entity;

import com.example.finalproject_leedaon.domain.dto.post.PostDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Post extends PostBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String body;

    // join
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public PostDto toPostDto() {
        return new PostDto(this.id, this.title, this.body, this.user.getUserName(), this.getCreatedAt(), this.getLastModifiedAt());
    }

    // 포스트 수정
    public void update(Post post) {
        this.title = post.getTitle();
        this.body = post.getBody();
    }
}