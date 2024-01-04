package com.green.greengram4.user;

import com.green.greengram4.common.Const;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.user.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserFollowMapper followMapper;

    public ResVo signup(UserSignupDto dto) {
        String hasedUpw = BCrypt.hashpw(dto.getUpw(), BCrypt.gensalt());
        UserSignupProcDto pDto = UserSignupProcDto.builder()
                .uid(dto.getUid())
                .nm(dto.getNm())
                .upw(hasedUpw)
                .pic(dto.getPic())
                .build();
        int insResult = userMapper.insUser(pDto);
        return new ResVo(pDto.getIuser());    //회원가입한 iuser pk 값 리턴
    }

    public UserSigninVo signin(UserSigninDto dto) {
        UserSigninProcVo procVo = userMapper.selLoginInfoByUid(dto);
        if (procVo == null) {
            return UserSigninVo.builder()
                    .result(Const.LOGIN_NO_UID)
                    .build();
        }
        if (BCrypt.checkpw(dto.getUpw(), procVo.getUpw())) {

            return UserSigninVo.builder()
                    .iuser(procVo.getIuser())
                    .nm(procVo.getNm())
                    .pic(procVo.getPic())
                    .result(Const.LOGIN_SUCCESS)
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

    public UserInfoVo getUserInfo(UserInfoSelDto dto){
        return userMapper.selUserInfo(dto);
    }

    public ResVo patchUserFirebaseToken(UserFirebaseTokenPatchDto dto) {
        return new ResVo(userMapper.updUserFirebaseToken(dto));
    }

    public ResVo patchUserPic(UserPicPatchDto dto) {
        return new ResVo(userMapper.updUserPic(dto));
    }
}
