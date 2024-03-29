package com.green.greengram4.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSigninDto {
    @Schema(defaultValue = "hubble")
    private String uid;
    @Schema(defaultValue = "xptmxm12")
    private String upw;
    @JsonIgnore
    private int iuser;
    @Schema(defaultValue = "LOCAL")
    private String providerType;
}
