package com.example.finalproject_leedaon.service;

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