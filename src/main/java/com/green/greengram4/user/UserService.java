package com.green.greengram4.user;

import com.green.greengram4.common.*;
import com.green.greengram4.entity.UserEntity;
import com.green.greengram4.entity.UserFollowEntity;
import com.green.greengram4.entity.UserFollowIds;
import com.green.greengram4.entity.UserFollowRepository;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.green.greengram4.common.Const.FAIL;
import static com.green.greengram4.common.Const.SUCCESS;

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
    //jpa
    private final UserRepository userRepository;
    private final UserFollowRepository followRepository;

    public ResVo signup(UserSignupDto dto) {
        UserEntity entity = UserEntity.builder()
                .providerType(ProviderTypeEnum.LOCAL)
                .uid(dto.getUid())
                .upw(passwordEncoder.encode(dto.getUpw()))
                .nm(dto.getNm())
                .pic(dto.getPic())
                .role(RoleEnum.USER)
                .build();
        userRepository.save(entity);    //entity 객체 리턴해줌
        return new ResVo(entity.getIuser().intValue());
    }

    public UserSigninVo signin(ProviderTypeEnum providerTypeEnum, HttpServletResponse res, UserSigninDto dto) {
        Optional<UserEntity> optEntity = userRepository.findByProviderTypeAndUid(providerTypeEnum, dto.getUid());
        UserEntity entity = optEntity.orElseThrow(() -> new RestApiException(AuthErrorCode.NOT_EXIST_USER_ID));

        if (!passwordEncoder.matches(dto.getUpw(), entity.getUpw())) {
            throw new RestApiException(AuthErrorCode.INVALID_PASSWORD);
        }
        int iuser = entity.getIuser().intValue();
        MyPrincipal myPrincipal = MyPrincipal.builder()
                .iuser(iuser)
                .build();
        myPrincipal.getRoles().add(entity.getRole().name());

        String at = jwtTokenProvider.generateAccessToken(myPrincipal);
        String rt = jwtTokenProvider.generateRefreshToken(myPrincipal);

        //cookie에 rt 담는 작업
        int rtCookieMaxAge = appProperties.getJwt().getRefreshCookieMaxAge();
        cookieUtils.deleteCookie(res, "rt");
        cookieUtils.setCookie(res, "rt", rt, rtCookieMaxAge);

//        HttpSession session = req.getSession(true);

        return UserSigninVo.builder()
                .iuser(entity.getIuser().intValue())
                .nm(entity.getNm())
                .pic(entity.getPic())
                .result(Const.LOGIN_SUCCESS)
                .accessToken(at)
                .build();
    }

    public ResVo toggleFollow(UserFollowDto dto) {
        UserFollowIds ids = new UserFollowIds();
        ids.setFromIuser((long) authenticationFacade.getLoginUserPk());
        ids.setToIuser(dto.getToIuser());
        Optional<UserFollowEntity> optEntity = followRepository.findById(ids);
        UserFollowEntity followEntity = optEntity.orElse(null);

        AtomicInteger atomicInteger = new AtomicInteger(FAIL);
        optEntity.ifPresentOrElse(
                followRepository::delete
                , () -> {
                    UserFollowEntity saveUserFollowEntity = new UserFollowEntity();
                    saveUserFollowEntity.setUserFollowIds(ids);
                    UserEntity fromUserEntity = userRepository.getReferenceById((long) authenticationFacade.getLoginUserPk());
                    UserEntity toUserEntity = userRepository.getReferenceById(dto.getToIuser());    //모두 다 세팅해서 작업해줘야 함.
                    saveUserFollowEntity.setFromUserEntity(fromUserEntity);
                    saveUserFollowEntity.setToUserEntity(toUserEntity);
                    followRepository.save(saveUserFollowEntity);
                    atomicInteger.set(SUCCESS);
                }
        );
        return new ResVo(atomicInteger.get());
    }

    public UserInfoVo getUserInfo(UserInfoSelDto dto) {
        return userMapper.selUserInfo(dto);
    }

    @Transactional
    public ResVo patchUserFirebaseToken(UserFirebaseTokenPatchDto dto) {
        UserEntity entity = userRepository.getReferenceById((long) authenticationFacade.getLoginUserPk());
        entity.setFirebaseToken(dto.getFirebaseToken());
//        userRepository.save(entity); 트랜잭션 걸면 save 안해도 됨.
        return new ResVo(SUCCESS);
    }

    @Transactional
    public UserPicPatchDto patchUserPic(MultipartFile pic) {
        Long iuser = (long) authenticationFacade.getLoginUserPk();
        UserEntity entity = userRepository.getReferenceById(iuser);

        String target = "/user/" + iuser;
        myFileUtils.delFolderTrigger(target);
        String saveFileNm = myFileUtils.transferTo(pic, target);
        entity.setPic(saveFileNm);

        return UserPicPatchDto.builder()
                .iuser(entity.getIuser().intValue())
                .pic(saveFileNm)
                .build();
    }

    public ResVo signout(HttpServletResponse res) {
        cookieUtils.deleteCookie(res, "rt");
        return new ResVo(SUCCESS);
    }

    public UserSigninVo getRefreshToken(HttpServletRequest req) {
//        Cookie cookie = cookieUtils.getCookie(req, "rt");
        Optional<String> optRt = cookieUtils.getCookie(req, "rt").map(Cookie::getValue);
        if (optRt.isEmpty()) {
            return UserSigninVo.builder()
                    .result(FAIL)
                    .accessToken(null)
                    .build();
        }
        String token = optRt.get();
        if (!jwtTokenProvider.isValidateToken(token)) {
            return UserSigninVo.builder()
                    .result(FAIL)
                    .accessToken(null)
                    .build();
        }

        MyUserDetails myUserDetails = (MyUserDetails) jwtTokenProvider.getUserDetailsFromToken(token);
        MyPrincipal myPrincipal = myUserDetails.getMyPrincipal();

        String at = jwtTokenProvider.generateAccessToken(myPrincipal);

        return UserSigninVo.builder()
                .result(SUCCESS)
                .accessToken(at)
                .build();
    }
}
