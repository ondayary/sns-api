package com.example.finalproject_leedaon.controller;

import com.example.finalproject_leedaon.domain.Response;
import com.example.finalproject_leedaon.domain.dto.alarm.AlarmResponse;
import com.example.finalproject_leedaon.service.AlarmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Alarm API")
@RequestMapping("/api/v1/alarms")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping
    @ApiOperation(value = "알람 조회", notes = "특정 사용자의 알람 목록을 페이징 형식으로 출력합니다.")
    public Response<Page<AlarmResponse>> alarmList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, Authentication authentication) {
        Page<AlarmResponse> alarmList = alarmService.alarmList(authentication.getName(), pageable);
        return Response.success(alarmList);
    }
}
