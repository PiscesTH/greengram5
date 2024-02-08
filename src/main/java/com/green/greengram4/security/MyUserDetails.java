package com.green.greengram4.security;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Data
@Builder
public class MyUserDetails implements UserDetails, OAuth2User { //요청이 왔을 때 authentication 에 넣는 용도

    private MyPrincipal myPrincipal;
    private Map<String, Object> attributes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {    //권한
        if (myPrincipal == null) {
            return null;
        }
        return this.myPrincipal.getRoles().stream()
//                .map(SimpleGrantedAuthority::new)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }

    @Override
    public String getPassword() {   //시큐리티 루틴 이용하면 구현 필요
        return null;
    }

    @Override
    public String getUsername() {   //시큐리티 루틴 이용하면 구현 필요
        return null;
    }

    //아래 메서드들은 로그인 커스텀 처리하면(시큐리티 루틴 안 탈때) 사용 안되는 메서드들.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    //여기까지

    @Override
    public String getName() {
        return null;
    }
}
