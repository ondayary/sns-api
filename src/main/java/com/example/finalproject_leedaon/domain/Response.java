package com.example.finalproject_leedaon.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response<T> { // 성공한 결과, 실패한 결과 둘다 담길 수 있다.
                           // 컨트롤러에서 최종으로 리턴해주는 리스폰스

    private String resultCode; // 결과에 따른 메세지
    private T result; // 성공인지 실패인지의 결과

    // 접속에 실패했을 때 - 에러메세지 반환
    public static <T> Response<T> error(String resultCode, T result) {
        return new Response(resultCode, result);
    }

    // 접속에 성공했을 때
    public static Response<Void> success() {
        return new Response("SUCCESS", null);
    }

    public static <T> Response<T> success(T result) {
        return new Response("SUCCESS", result);
    }
}










