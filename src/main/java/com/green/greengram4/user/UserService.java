package com.green.greengram4.user;

import com.green.greengram4.common.*;
import com.green.greengram4.exception.AuthErrorCode;
import com.green.greengram4.exception.RestApiException;
import com.green.greengram4.security.AuthenticationFacade;
import com.green.greengram4.security.JwtTokenProvider;
import com.green.greengram4.security.MyPrincipal;
import com.green.greengram4.security.MyUserDetails;
import com.green.greengram4.user.model.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final AuthenticationFacade authenticationFacade;
    private final MyFileUtils myFileUtils;

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
            throw new RestApiException(AuthErrorCode.NOT_EXIST_USER_ID);    //런타임 예외는 throws 없어도 가능 한 듯 ?
        }
        if (!passwordEncoder.matches(dto.getUpw(), procVo.getUpw())) {
            throw new RestApiException(AuthErrorCode.INVALID_PASSWORD);
        }

        MyPrincipal principal = MyPrincipal.builder()
                .iuser(procVo.getIuser())
                .build();
        principal.getRoles().add(procVo.getRole());
        log.info("principal : {}", principal);

        String at = jwtTokenProvider.generateAccessToken(principal);
        String rt = jwtTokenProvider.generateRefreshToken(principal);

        //cookie에 rt 담는 작업
        int rtCookieMaxAge = appProperties.getJwt().getRefreshCookieMaxAge();
        cookieUtils.deleteCookie(res, "rt");
        cookieUtils.setCookie(res, "rt", rt, rtCookieMaxAge);

//        HttpSession session = req.getSession(true);

        return UserSigninVo.builder()
                .iuser(procVo.getIuser())
                .nm(procVo.getNm())
                .pic(procVo.getPic())
                .result(Const.LOGIN_SUCCESS)
                .accessToken(at)
                .build();

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

    public UserPicPatchDto patchUserPic(MultipartFile pic) {
        int iuser = authenticationFacade.getLoginUserPk();
        String target = "user/" + iuser;
        myFileUtils.delFolderTrigger(target);
        String saveFileNm = myFileUtils.transferTo(pic, target);
        UserPicPatchDto dto = UserPicPatchDto.builder()
                .iuser(iuser)
                .pic(saveFileNm)
                .build();

        log.info("pic : {}", saveFileNm);
        log.info("iuser : {}", dto.getIuser());
        int result = userMapper.updUserPic(dto);
        return dto;
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
