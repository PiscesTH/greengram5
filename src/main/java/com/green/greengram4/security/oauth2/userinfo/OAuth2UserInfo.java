package com.green.greengram4.security.oauth2.userinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public abstract class OAuth2UserInfo {  //소셜로그인 후에 데이터를 받을 수 있는 객체
    protected Map<String, Object> attributes;

    public abstract String getId();
    public abstract String getName();
    public abstract String getEmail();
    public abstract String getImageUrl();
}
