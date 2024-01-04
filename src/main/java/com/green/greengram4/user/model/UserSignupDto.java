package com.green.greengram4.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "회원가입 데이터")
public class UserSignupDto {
    @Schema(title = "아이디", example = "mic")
    private String uid;
    @Schema(title = "비밀번호", example = "1234")
    private String upw;
    private String nm;
    private String pic;
}
