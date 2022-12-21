package com.example.finalproject_leedaon.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response<T> { // 성공한 결과, 실패한 결과 둘다 담길 수 있다.

    private String resultCode; // 결과에 따른 메세지
    private T result; // 성공인지 실패인지의 결과

    // 접속에 실패했을 때 - 에러메세지 반환
    public static Response<Void> error(String resultCode) {
        return new Response(resultCode, null);
    }

    // 접속에 성공했을 때
    public static <T> Response<T> success(T result) {
        return new Response("SUCCESS", result);
    }
}
