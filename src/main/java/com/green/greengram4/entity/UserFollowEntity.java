package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_user_follow")
public class UserFollowEntity extends CreatedAtEntity {
    @EmbeddedId //복합키 설정
    private UserFollowIds userFollowIds;

    @ManyToOne(optional = false)    //false 줘야 inner join 가능 ? 없으면 컬럼 증가
    @MapsId("fromIuser")    //멤버필드명, 복합키 걸 때 사용
    @JoinColumn(name = "from_iuser", columnDefinition = "BIGINT UNSIGNED")
    private UserEntity fromUserEntity;

    @ManyToOne(optional = false)    //관계 설정. 외래키 설정
    @MapsId("toIuser")
    @JoinColumn(name = "to_iuser", columnDefinition = "BIGINT UNSIGNED")
    private UserEntity toUserEntity;
}
