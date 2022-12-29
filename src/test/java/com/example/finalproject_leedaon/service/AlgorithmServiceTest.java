package com.example.finalproject_leedaon.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// annotation 없이 만든다.
class AlgorithmServiceTest {

    // Spring을 안쓰고 테스트 하기 때문에 new를 이용해 초기화를 해준다.
    // Pojo방식을 최대한 활용한다.

    AlgorithmService algorithmService = new AlgorithmService();

    @Test
    @DisplayName("자릿수 합 잘 구하는지")
    void sumOfDigit() {
        assertEquals(21, algorithmService.sumOfDigit(687));
        assertEquals(22, algorithmService.sumOfDigit(787));
        assertEquals(0, algorithmService.sumOfDigit(0));
        assertEquals(5, algorithmService.sumOfDigit(11111));
    }
}