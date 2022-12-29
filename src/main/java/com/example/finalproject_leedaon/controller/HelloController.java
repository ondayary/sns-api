package com.example.finalproject_leedaon.controller;

import com.example.finalproject_leedaon.service.AlgorithmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hello")
public class HelloController {

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
