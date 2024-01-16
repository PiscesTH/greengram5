package com.green.greengram4.user.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UserPicPatchDto {
    private int iuser;
    private MultipartFile pic;
}
