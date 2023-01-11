package com.example.finalproject_leedaon.domain.dto.alarm;

import com.example.finalproject_leedaon.domain.entity.Alarm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AlarmResponse {

    /*
        {
            "id": 1,
            "alarmType": "NEW_LIKE_ON_POST",
            "fromUserId": 1,
            "targetId": 1,
            "text": "new like!",
            "createdAt": "2022-12-25T14:53:28.209+00:00"
        }
     */

    private Integer id;
    private AlarmType alarmType;
    private Integer fromUserId;
    private Integer targetId;
    private String text;
    private LocalDateTime createdAt;

    public static Page<AlarmResponse> toAlarmResponse(Page<Alarm> alarms) {
        Page<AlarmResponse> alarmResponsePage = alarms.map(
                alarm -> AlarmResponse.builder()
                .id(alarm.getId())
                .alarmType(alarm.getAlarmType())
                .fromUserId(alarm.getFromUserId())
                .targetId(alarm.getTargetId())
                .text(alarm.getText())
                .createdAt(alarm.getCreatedAt())
                .build());

        return alarmResponsePage;
    }
}
