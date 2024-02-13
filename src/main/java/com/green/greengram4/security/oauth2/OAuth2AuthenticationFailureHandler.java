package com.green.greengram4.security.oauth2;

import com.green.greengram4.common.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler {
    private final CookieUtils cookieUtils;
}
