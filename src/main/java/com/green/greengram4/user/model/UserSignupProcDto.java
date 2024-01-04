package com.green.greengram4.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSignupProcDto {
    private String uid;
    private String upw;
    private String nm;
    private String pic;
    private int iuser;
}
