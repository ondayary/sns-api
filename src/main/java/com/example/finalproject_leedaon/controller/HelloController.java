package com.example.finalproject_leedaon.controller;

import com.example.finalproject_leedaon.service.AlgorithmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hello")
public @ApiIgnore class HelloController {

    // 반환타입 앞에 명시한 @ApiIgnore는 해당 method를 Swagger UI 에서 노출되지 않게 한다.

    private final AlgorithmService algorithmService;

    @GetMapping
    public String hello() {
        return "이다온";
    }

    @GetMapping("/{num}")
    public Integer divide(@PathVariable Integer num) {
        return algorithmService.sumOfDigit(num);
    }
}
