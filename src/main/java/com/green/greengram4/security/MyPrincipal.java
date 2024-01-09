package com.green.greengram4.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPrincipal {  //토큰에 넣을 때 사용하는 용도 ?
    private int iuser;
}
