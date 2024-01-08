package com.green.greengram4.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;

@Slf4j
@Component
public class JwtTokenProvider {
    private final String secret;    //암호화 할 때 사용하는 키 ?
    private Key key;

    public JwtTokenProvider(@Value("${springboot.jwt.secret}") String secret) {
        this.secret = secret;
    }

    @PostConstruct  //DI되고 나서 메서드 호출 하는 방법
    public void init() {
        log.info("secret : {}", secret);
        byte[] keyBytes = null;
    }
}
