package com.green.greengram4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration  // 설정파일 어노테이션
public class CharEncodingConfig {

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        return new CharacterEncodingFilter("UTF-8", true);
    }
}
