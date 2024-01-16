package com.green.greengram4.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPicPatchDto {
    private int iuser;
    private String pic;
}
