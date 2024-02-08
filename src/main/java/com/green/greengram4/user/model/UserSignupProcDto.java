package com.green.greengram4.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupProcDto {
    private String uid;
    private String upw;
    private String nm;
    private String pic;
    private int iuser;
    private String providerType;
    private String role;
}
