package com.green.greengram4.entity;

import com.green.greengram4.common.ProviderTypeEnum;
import jakarta.persistence.*;

@Entity //pk적용 필수
@Table(name = "t_user")
public class UserEntity extends BaseEntity {
    @Id //pk설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment 설정. AUTO -> 시퀀스 사용
    private Long iuser;

    @Column(length = 10, name = "provider_type", nullable = false)
    //pk외의 컬럼 설정, 길이, 이름, null허용 설정 가능
    private ProviderTypeEnum providerType;

    @Column(length = 100, nullable = false)
    private String uid;

    @Column(length = 300, nullable = false)
    private String upw;

    @Column(length = 25, nullable = false)
    private String nm;

    @Column(length = 2100)
    private String pic;

    @Column(length = 10, nullable = false)
    private String role;
}
