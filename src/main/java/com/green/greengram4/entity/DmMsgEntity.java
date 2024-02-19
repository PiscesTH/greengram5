package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_dm_msg")
public class DmMsgEntity extends CreatedAtEntity {
    @EmbeddedId
    private DmMsgIds dmMsgIds;

    @ManyToOne(optional = false)
    @MapsId("idm")  //없으면 새로운 컬럼 생김. 복합키에 있는 컬럼과 연결 해주는 역할
    @JoinColumn(name = "idm", columnDefinition = "BIGINT UNSIGNED")
    private DmEntity dmEntity;

//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(columnDefinition = "BIGINT UNSIGNED")
//    private Long seq;

    @ManyToOne(optional = false)
    @JoinColumn(name = "iuser", nullable = false) //nullable = false 설정되면 inner join 하게 됨.
    private UserEntity userEntity;

    @Column(length = 2000, nullable = false)
    private String msg;
}
