package com.green.greengram4.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram4.common.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    //    @Value("${springboot.jwt.secret}")    생성자 이용 안할 때. final 없어야 함
//    private final String secret;    //암호화 할 때 사용하는 키 ?
//    private final String headerSchemeName;
//    private final String tokenType;
    private final ObjectMapper om;
    private final AppProperties appProperties;  //객체로 처리
    private Key key;

/*    public JwtTokenProvider(@Value("${springboot.jwt.secret}") String secret,
                            @Value("${springboot.jwt.header-scheme-name}") String headerSchemeName,
                            @Value("${springboot.jwt.token-type}") String tokenType) {
        this.secret = secret;
        this.headerSchemeName = headerSchemeName;
        this.tokenType = tokenType;
    }*/

    @PostConstruct  //사용조건 : 빈등록 -> DI되고 나서 메서드 호출 하는 방법
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(appProperties.getJwt().getSecret());
        log.info("keyBytes : {}", keyBytes);
        this.key = Keys.hmacShaKeyFor(keyBytes);    //키 만드는 법
    }

    public String generateToken(MyPrincipal principal, long tokenValidMs) {
        return Jwts.builder()
                .claims(createClaims(principal))    //토큰에 담기는 정보
                .issuedAt(new Date(System.currentTimeMillis()))     //발행시간 설정
                .expiration(new Date(System.currentTimeMillis() + tokenValidMs))    //만료시간 설정
                .signWith(this.key)
                .compact();
    }

    private Claims createClaims(MyPrincipal principal) {    //Claims : key와 value 저장 가능
        try {
            String json = om.writeValueAsString(principal);
            return Jwts.claims()
                    .add("user", json)
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

    public String resolveToken(HttpServletRequest req) {
        String auth = req.getHeader(appProperties.getJwt().getHeaderSchemeName());
        if (auth == null) {
            return null;
        }
        if (auth.startsWith(appProperties.getJwt().getTokenType())) {
            return auth.substring(appProperties.getJwt().getTokenType().length()).trim();
        }
        return null;
//        return auth == null ? null : auth.startsWith();
    }

    public boolean isValidateToken(String token) {
        try {
            return !getAllClaims(token).getExpiration().before(new Date());
            //만료시간이 현재시간보다 전이면 false, 후면 true
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Authentication getAuthentication(String token) {     //Authentication 에 담을 때 사용 ?
        UserDetails userDetails = getUserDetailsFromToken(token);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private UserDetails getUserDetailsFromToken(String token) {
        try {
            Claims claims = getAllClaims(token);
            String json = (String) claims.get("user");
            MyPrincipal myPrincipal = om.readValue(json, MyPrincipal.class);
            return MyUserDetails.builder()
                    .myPrincipal(myPrincipal)
                    .build();
        } catch (Exception e) {
            return null;
        }
    }
}
