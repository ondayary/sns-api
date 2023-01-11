package com.example.finalproject_leedaon.configuration;

import com.example.finalproject_leedaon.service.UserService;
import com.example.finalproject_leedaon.utils.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/swagger-ui/").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/users/join", "/api/v1/users/login").permitAll()
                .antMatchers(HttpMethod.POST).authenticated()
                .antMatchers(HttpMethod.GET, "/api/v1/posts/my", "/api/v1/alarms").authenticated()
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers(HttpMethod.PUT).authenticated()
                .antMatchers(HttpMethod.DELETE).authenticated()
                .and()
                .sessionManagement() // 인증, 인가 관리
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt사용하는 경우 씀, 상태없음 정책
                .and()
                .addFilterBefore(new JwtFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class) //UserNamePasswordAuthenticationFilter적용하기 전에 JWTTokenFilter를 적용 하라는 뜻 입니다.
                .build();
    }
}

/*
 *  .csrf().disable(): 세션을 사용하지 않고 JWT 토큰을 활용하여 진행하고 REST API를 만드는 작업이기때문에 csrf 사용은 disable 처리한다.
 *  .cors().and(): 도메인이 다를 때 허용하겠다.
 *  .authorizeRequests(): 이제부터 인증 절차에 대한 설정을 진행하겠다는 것이다.
 *  .antMatchers("/swagger-ui/"): 특정 URL에 대해서 어떻게 인증 처리를 할 것인지 결정한다.
 *  .permitAll(): 스프링 시큐리티에서 인증이 되지 않더라도 통과시켜 누구에게나 사용을 열어준다.
 *  .authenticated() : 요청 내에 스프링 시큐리티 컨텍스트 내에서 인증이 완료되어야 api를 사용할 수 있다. 인증이 되지 않은 요청은 403(Forbidden)이 내려진다.
 *  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS): 현재 스프링 시큐리티에서 세션을 관리하지 않겠다는 뜻이다.
 *  서버에서 관리되는 세션 없이 클라이언트에서 요청하는 헤더에 token을 담아보낸다면 서버에서 토큰을 확인하여 인증하는 방식을 사용할 것이므로 서버에서 관리되어야할 세션이 필요없어지게 된다.
 *
 *  위에서 아래로 읽히기 때문에 사용을 열어주는 것이 더 적으면 먼저 읽히게끔 하고 다음에 전체 제한이 오게 하고 제한하는 것이 더 적으면 제한 하는 것을 먼저 읽히게끔하고 전체를 열어준다.
 */
