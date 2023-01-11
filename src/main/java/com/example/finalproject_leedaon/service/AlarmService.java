package com.example.finalproject_leedaon.service;

import com.example.finalproject_leedaon.domain.dto.alarm.AlarmResponse;
import com.example.finalproject_leedaon.domain.entity.Alarm;
import com.example.finalproject_leedaon.domain.entity.User;
import com.example.finalproject_leedaon.exception.AppException;
import com.example.finalproject_leedaon.repository.AlarmRepository;
import com.example.finalproject_leedaon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.finalproject_leedaon.exception.ErrorCode.USERNAME_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    public Page<AlarmResponse> alarmList(String userName, Pageable pageable) {

        // userName이 없는 경우
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(USERNAME_NOT_FOUND, USERNAME_NOT_FOUND.getMessage()));

        // 사용자의 알람 검색
        Page<Alarm> alarm = alarmRepository.findByUserId(user.getId(), pageable);
        Page<AlarmResponse> alarmResponsePage = AlarmResponse.toAlarmResponse(alarm);

        return alarmResponsePage;
    }
}
