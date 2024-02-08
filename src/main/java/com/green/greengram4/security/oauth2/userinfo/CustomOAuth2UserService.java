package com.green.greengram4.security.oauth2.userinfo;

import com.green.greengram4.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserMapper mapper;
    private final OAuth2UserInfoFactory factory;
}
