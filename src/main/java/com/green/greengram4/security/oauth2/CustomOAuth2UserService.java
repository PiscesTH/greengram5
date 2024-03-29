package com.green.greengram4.security.oauth2;

import com.green.greengram4.security.MyPrincipal;
import com.green.greengram4.security.MyUserDetails;
import com.green.greengram4.security.oauth2.SocialProviderType;
import com.green.greengram4.security.oauth2.userinfo.OAuth2UserInfo;
import com.green.greengram4.security.oauth2.userinfo.OAuth2UserInfoFactory;
import com.green.greengram4.user.UserMapper;
import com.green.greengram4.user.model.UserSigninDto;
import com.green.greengram4.user.model.UserSigninProcVo;
import com.green.greengram4.user.model.UserSignupProcDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserMapper mapper;
    private final OAuth2UserInfoFactory factory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
//        userRequest.getClientRegistration().getRegistrationId()   -> ex)naver / kakao
        SocialProviderType socialProviderType = SocialProviderType.valueOf(userRequest.getClientRegistration()
                .getRegistrationId()
                .toUpperCase());

        Map<String, Object> attributes = user.getAttributes();
        OAuth2UserInfo oAuth2UserInfo = factory.getOAuth2UserInfo(socialProviderType, attributes);

        UserSigninDto dto = UserSigninDto.builder()
                .providerType(socialProviderType.name())
                .uid(oAuth2UserInfo.getId())
                .build();
        UserSigninProcVo savedUser = mapper.selLoginInfoByUid(dto);
        if (savedUser == null) {
            savedUser = signupUser(oAuth2UserInfo, socialProviderType);
        }
        MyPrincipal myPrincipal = MyPrincipal.builder()
                .iuser(savedUser.getIuser())
                .build();
        myPrincipal.getRoles().add(savedUser.getRole());

        return MyUserDetails.builder()
                .userEntity(savedUser)
                .attributes(user.getAttributes())
                .myPrincipal(myPrincipal)
                .build();
    }

    private UserSigninProcVo signupUser(OAuth2UserInfo oAuth2UserInfo, SocialProviderType socialProviderType) {
        UserSignupProcDto dto = UserSignupProcDto.builder()
                .providerType(socialProviderType.name())
                .uid(oAuth2UserInfo.getId())
                .upw("social")
                .nm(oAuth2UserInfo.getName())
                .pic(oAuth2UserInfo.getImageUrl())
                .role("USER")
                .build();
        int insResult = mapper.insUser(dto);

        UserSigninProcVo vo = new UserSigninProcVo();
        vo.setIuser(dto.getIuser());
        vo.setRole(dto.getRole());
        vo.setNm(dto.getNm());
        vo.setPic(dto.getPic());
        vo.setUid(dto.getUid());
        return vo;
    }
}
