package com.green.greengram4.user.model;

import lombok.Data;

@Data
public class UserSigninProcVo {
    private int iuser;
    private String nm;
    private String pic;
    private String upw;
    private String role;
    private String providerType;
}
