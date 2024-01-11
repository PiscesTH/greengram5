package com.green.greengram4.user;

import com.green.greengram4.common.AppProperties;
import com.green.greengram4.common.Const;
import com.green.greengram4.common.CookieUtils;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.security.JwtTokenProvider;
import com.green.greengram4.security.MyPrincipal;
import com.green.greengram4.security.MyUserDetails;
import com.green.greengram4.user.model.*;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserFollowMapper followMapper;
    private final PasswordEncoder passwordEncoder;  //SecurityConfiguration 에서 빈등록 하고있음.
    private final JwtTokenProvider jwtTokenProvider;
    private final AppProperties appProperties;
    private final CookieUtils cookieUtils;

    public ResVo signup(UserSignupDto dto) {
//        String hasedUpw = BCrypt.hashpw(dto.getUpw(), BCrypt.gensalt());
        String hasedUpw = passwordEncoder.encode(dto.getUpw());
        UserSignupProcDto pDto = UserSignupProcDto.builder()
                .uid(dto.getUid())
                .nm(dto.getNm())
                .upw(hasedUpw)
                .pic(dto.getPic())
                .build();
        int insResult = userMapper.insUser(pDto);
        return new ResVo(pDto.getIuser());    //회원가입한 iuser pk 값 리턴
    }

    public UserSigninVo signin(HttpServletResponse res, UserSigninDto dto) {
        UserSigninProcVo procVo = userMapper.selLoginInfoByUid(dto);
        if (procVo == null) {
            return UserSigninVo.builder()
                    .result(Const.LOGIN_NO_UID)
                    .build();
        }
        if (passwordEncoder.matches(dto.getUpw(), procVo.getUpw())) {
            MyPrincipal principal = new MyPrincipal(procVo.getIuser());
//            String at = jwtTokenProvider.generateToken(principal, appProperties.getJwt().getAccessTokenExpiry());
//            String rt = jwtTokenProvider.generateToken(principal, appProperties.getJwt().getRefreshTokenExpiry());
            String at = jwtTokenProvider.generateAccessToken(principal);
            String rt = jwtTokenProvider.generateRefreshToken(principal);

            //cookie에 rt 담는 작업
            int rtCookieMaxAge = appProperties.getJwt().getRefreshCookieMaxAge();
            cookieUtils.deleteCookie(res, "rt");
            cookieUtils.setCookie(res, "rt", rt, rtCookieMaxAge);

            return UserSigninVo.builder()
                    .iuser(procVo.getIuser())
                    .nm(procVo.getNm())
                    .pic(procVo.getPic())
                    .result(Const.LOGIN_SUCCESS)
                    .accessToken(at)
                    .build();
        }
        return UserSigninVo.builder().result(Const.LOGIN_DIFF_UPW).build();
    }

    public ResVo toggleFollow(UserFollowDto dto) {
        int delResult = followMapper.delFollow(dto);
        if (delResult == 1) {
            return new ResVo(Const.FAIL);
        }
        try {
            int insResult = followMapper.insFollow(dto);
            return new ResVo(Const.SUCCESS);
        } catch (Exception e) {
            return new ResVo(Const.FAIL);
        }
    }

    public UserInfoVo getUserInfo(UserInfoSelDto dto) {
        return userMapper.selUserInfo(dto);
    }

    public ResVo patchUserFirebaseToken(UserFirebaseTokenPatchDto dto) {
        return new ResVo(userMapper.updUserFirebaseToken(dto));
    }

    public ResVo patchUserPic(UserPicPatchDto dto) {
        return new ResVo(userMapper.updUserPic(dto));
    }

    public ResVo signout(HttpServletResponse res) {
        cookieUtils.deleteCookie(res, "rt");
        return new ResVo(Const.SUCCESS);
    }

    public UserSigninVo getRefreshToken(HttpServletRequest req) {
        Cookie cookie = cookieUtils.getCookie(req, "rt");
        if (cookie == null) {
            return UserSigninVo.builder()
                    .result(Const.FAIL)
                    .accessToken(null)
                    .build();
        }
        String token = cookie.getValue();
        if (!jwtTokenProvider.isValidateToken(token)) {
            return UserSigninVo.builder()
                    .result(Const.FAIL)
                    .accessToken(null)
                    .build();
        }

        MyUserDetails myUserDetails = (MyUserDetails) jwtTokenProvider.getUserDetailsFromToken(token);
        MyPrincipal myPrincipal = myUserDetails.getMyPrincipal();

        String at = jwtTokenProvider.generateAccessToken(myPrincipal);

        return UserSigninVo.builder()
                .result(Const.SUCCESS)
                .accessToken(at)
                .build();
    }
}
