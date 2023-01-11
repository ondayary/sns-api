package com.example.finalproject_leedaon.domain.dto.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmType {
    NEW_COMMENT_ON_POST("new comment!"),
    NEW_LIKE_ON_POST("new like!");

    private String alarmText;
}
