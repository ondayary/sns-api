package com.example.finalproject_leedaon.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtUtil {

    // 토큰에서 UserName꺼내 Controller에 전달하기
    public static String getUserName(String token, String secretKey){
        return extractClaims(token,secretKey).get("userName").toString();
    }

    // 토큰이 만료된 경우 접근 막기
    public static boolean isExpired(String token, String secretKey) {
        Date expiredDate = extractClaims(token, secretKey).getExpiration();
        return expiredDate.before(new Date());
    }

   public static String createToken(String userName, String secretKey, long expireTimeMs) {
        Claims claims = Jwts.claims(); // 일종의 map
        claims.put("userName", userName); // 넣고 싶은 정보를 claims에 넣는다.
        
        return Jwts.builder()
                .setClaims(claims) // claims 지정
                .setIssuedAt(new Date(System.currentTimeMillis())) // 만든 날짜
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs)) // 끝나는 날짜
                .signWith(SignatureAlgorithm.HS256, secretKey) // 이 키를 이용해 암호화를 한다.
                .compact();
    }

    private static Claims extractClaims(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}

