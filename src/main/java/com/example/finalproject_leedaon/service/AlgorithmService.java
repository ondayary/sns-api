package com.example.finalproject_leedaon.service;

import org.springframework.stereotype.Service;

@Service // 서비스에 빈으로 등록해주는 기능이 있어서 서비스를 달아주면 빈으로 등록해준다.
public class AlgorithmService {

    public Integer sumOfDigit(Integer num) {
        // 687
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num = num / 10;
        }
        return sum;
    }
}