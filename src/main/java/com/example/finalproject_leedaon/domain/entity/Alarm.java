package com.example.finalproject_leedaon.domain.entity;

import com.example.finalproject_leedaon.domain.dto.alarm.AlarmType;
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
public class Alarm extends PostBase { // 알람 기능

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private AlarmType alarmType;
    private LocalDateTime deleted_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // created_at과 last_modified_at은 PostBase에 구현

    private int fromUserId; // 알림을 발생시킨 user id
    private int targetId; // 알림이 발생된 post id
    private String text; // alarmType에 따라 string 필드에 담아 줄 수 있도록 필드를 선언합니다.

    public static Alarm of(AlarmType alarmType, User user, Integer fromUserId, Integer targetId) {
        return Alarm.builder()
                .alarmType(alarmType)
                .user(user)
                .fromUserId(fromUserId)
                .targetId(targetId)
                .text(alarmType.getAlarmText())
                .build();
    }
}
