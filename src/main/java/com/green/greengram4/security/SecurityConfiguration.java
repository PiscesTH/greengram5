package com.green.greengram4.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {    //인증 & 인가 담당

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))    //설정 -> session 사용 x (로그인에 세션 안 써서)
                .httpBasic(http -> http.disable())  //설정 -> 시큐리티에서 서버사이드 렌더링 되는 로그인 화면 사용 x
                .formLogin(formLogin -> formLogin.disable())
                .csrf(csrf -> csrf.disable())   //설정 -> 기본적으로 스프링이 제공해주는 보안 기법 사용 x     cos, csrf 검색 참고
                .authorizeHttpRequests(author -> author.requestMatchers("/api/user/signin",    //requestMatchers() : 요청이 다음과 같으면
                                                                        "/api/user/signup",
                                                                        "/error",
                                                                        "/err",
                                                                        "/",
                                                                        "/index.html",
                                                                        "/static/**",
                                                                        "/swagger.html",    //스웨거 사용을 위한 설정 3줄
                                                                        "/swagger-ui/**",
                                                                        "/v3/api-docs/**"
                            ).permitAll()  //permitAll() : 무조건 통과
                        //.requestMatchers(HttpMethod.GET, "/product/**").permitAll() //해당 주소값의 get요청만 허용
                        //.requestMatchers(HttpMethod.POST, "/product/**").permitAll() //해당 주소값의 post요청만 허용
                        //.requestMatchers("/todo-api").hasAnyRole("USER", "ADMIN")   //해당 요청 가능한 권한(역할) 지정
                        //.anyRequest().hasRole("ADMIN")  //이외의 모든 요청은 ADMIN만 가능
                        .anyRequest().authenticated()   //이외의 요청은 로그인 해야만(authenticated()) 사용 가능
                )
                .exceptionHandling(except -> {  //이 세팅 안하면 whitelabel에러만 나옴
                    except.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                            .accessDeniedHandler(new JwtAccessDeniedHandler());
                })
                .build();  //적는 순서 중요
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
