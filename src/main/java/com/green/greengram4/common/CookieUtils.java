package com.green.greengram4.common;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Base64;
import java.util.Optional;

@Component
public class CookieUtils {
    public Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }

    public void setCookie(HttpServletResponse response, String name, String value, int maxAge) {    //maxAge : 쿠키 유효시간(단위 : 초)
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");    //호스팅 주소값 기준으로 쿠키가 만들어짐. 루트 주소값 설정. 모든 주소값에서 쿠키  사용 가능하다 ?
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie); //response에 쿠키 담아서 응답하게 됨.
    }

    public void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public String serialize(Object object) {
        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(object)); //byte -> String
    }

    public <T> T deserialize(Cookie cookie, Class<T> cls) { //직렬화 : 객체 -> 제이슨 형태 / deserialize : 역직렬화
        return cls.cast(
                SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue())
                )
        );
    }
}
