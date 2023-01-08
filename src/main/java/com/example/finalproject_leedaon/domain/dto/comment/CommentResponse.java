package com.example.finalproject_leedaon.domain.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    /*
        {
            "id": 3,
            "comment": "comment test3",
            "userName": "test",
            "postId": 2,
            "createdAt": "2022-12-20T16:07:25.699346"
        },
     */

    private Integer id;
    private String comment;
    private String userName;
    private Integer postId;
    private LocalDateTime createdAt;
}