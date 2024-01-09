package com.green.greengram4.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "app")    //프로그램 시작 클래스에 @ConfigurationPropertiesScan 필요
public class AppProperties {    //yml에 작성한 property 값 가져오는 클래스

    private final Jwt jwt = new Jwt();

    @Getter
    @Setter
    public class Jwt{
        private String secret;
        private String headerSchemeName;
        private String tokenType;
        private long accessTokenExpiry;
        private long refreshTokenExpiry;
    }
}
