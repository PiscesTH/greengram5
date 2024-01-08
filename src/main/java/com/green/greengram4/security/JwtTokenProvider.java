package com.green.greengram4.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
//    @Value("${springboot.jwt.secret}")    생성자 이용 안할 때. final 없어야 함
    private final String secret;    //암호화 할 때 사용하는 키 ?
    private final String headerSchemeName;
    private final String tokenType;
    private Key key;

    public JwtTokenProvider(@Value("${springboot.jwt.secret}") String secret,
                            @Value("${springboot.jwt.header-scheme-name}") String headerSchemeName,
                            @Value("${springboot.jwt.token-type}") String tokenType) {
        this.secret = secret;
        this.headerSchemeName = headerSchemeName;
        this.tokenType = tokenType;
    }

    @PostConstruct  //사용조건 : 빈등록 -> DI되고 나서 메서드 호출 하는 방법
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        log.info("keyBytes : {}", keyBytes);
        this.key = Keys.hmacShaKeyFor(keyBytes);    //키 만드는 법
    }

    public String generateToken(MyPrincipal principal, long tokenValidMs) {
        return Jwts.builder()
                .claims(createClaims(principal))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenValidMs))
                .signWith(this.key)
                .compact();
    }

    private Claims createClaims(MyPrincipal principal) {
        return Jwts.claims()
                .add("iuser", principal.getIuser())
                .build();
    }

    public String resolveToken(HttpServletRequest req) {
        String auth = req.getHeader(headerSchemeName);
        if (auth == null) { return null; }
        if (auth.startsWith(tokenType)){
            return auth.substring(tokenType.length()).trim();
        }
        return null;
//        return auth == null ? null : auth.startsWith();
    }
}
