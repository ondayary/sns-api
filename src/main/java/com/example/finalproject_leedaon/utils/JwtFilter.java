package com.example.finalproject_leedaon.utils;


import com.example.finalproject_leedaon.domain.entity.User;
import com.example.finalproject_leedaon.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 헤더에 토큰이 없을 경우에 접근 막기
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorizationHeader : {}", authorizationHeader);

        // 만약에 authorizationHeader가 없는 경우와
        // authorizationHeader가 있는데 typq이 Bearer 토큰이 아니라면 접근이 불가능하다.
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.error("authorization을 잘못보냈습니다."); //  이것이 출력된다면 authorization이 필요한 것
            filterChain.doFilter(request, response);
            return;
            // 토큰이 포함되지 않은 채 요청을 하면 접근이 불가하다.
        }

        // token 꺼내기
        String token;

        try {
            token = authorizationHeader.split(" ")[1];
        } catch (Exception e) {
            log.error("token 추출에 실패했습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰이 만료된 경우 접근 막기 - 토큰에는 만료기간이 존재하기 때문
        if(JwtUtil.isExpired(token,secretKey)){
            filterChain.doFilter(request,response);
            log.error("token이 만료되었습니다.");
            return;
        }

        // 토큰에서 userName을 추출하기
        String userName = JwtUtil.getUserName(token,secretKey);
        log.info("user Name: {}", userName);

        User user = userService.getUserByUserName(userName);
        log.info("userRole: {}", user.getRole());

        // Enable authentication if token is valid
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUserName(),
                null,
                List.of(new SimpleGrantedAuthority(user.getRole().name())));
        log.info("Authentication enabled : {}", usernamePasswordAuthenticationToken);
        log.info("A granted authority textual role : {}", user.getRole().name());

        // 권한 부여하기
        // Set authenticationtoken details
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        log.info("Authentication details : {}", usernamePasswordAuthenticationToken.getDetails().toString());

        // Set authentication for Security context
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        log.info("Security Context : {}", SecurityContextHolder.getContext().getAuthentication().toString());

        filterChain.doFilter(request, response);

        // 이 코드로 인해서 postController 접근자체가 불가했었다. (기록 위해 남겨두기)
        /*
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("", null, List.of(new SimpleGrantedAuthority("USER")));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
        */
    }
}