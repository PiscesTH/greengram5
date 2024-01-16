package com.green.greengram4.user;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;

    @Operation(summary = "회원가입", description = "회원가입용 정보")
    @PostMapping("/signup")
    public ResVo postSignup(@RequestBody UserSignupDto dto) {
        return service.signup(dto);
    }

    @Operation(summary = "로그인", description = "result = 1: 로그인 성공 / 2: 아이디 없음 / 3: 비밀번호 틀림")
    @PostMapping("/signin")
    public UserSigninVo postSignin(HttpServletResponse res,
                                   @RequestBody UserSigninDto dto) {
        return service.signin(res, dto);
    }

    @PostMapping("/signout")
    public ResVo postSignout(HttpServletRequest req, HttpServletResponse res) {
        return service.signout(res);
    }

    @GetMapping("/refresh-token")
    public UserSigninVo getRefreshToken(HttpServletRequest req) {
        return service.getRefreshToken(req);
    }

    @Operation(summary = "팔로우 처리")
    @PostMapping("/follow")
    public ResVo toggleFollow(@RequestBody UserFollowDto dto) {
        return service.toggleFollow(dto);
    }

    @Operation(summary = "유저 프로필 정보")
    @GetMapping
    public UserInfoVo getUserInfo(UserInfoSelDto dto) {
        return service.getUserInfo(dto);
    }

    @PatchMapping("/firebase-token")
    public ResVo patchUserFirebaseToken(@RequestBody UserFirebaseTokenPatchDto dto) {
        return service.patchUserFirebaseToken(dto);
    }

    @PatchMapping("/pic")
    public ResVo patchUserPic(@RequestPart MultipartFile pic) {
        return service.patchUserPic(pic);
    }
}
