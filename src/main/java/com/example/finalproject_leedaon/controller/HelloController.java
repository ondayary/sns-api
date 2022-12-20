package com.example.finalproject_leedaon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/v1/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok().body("hello");
    }
}
