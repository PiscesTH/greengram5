package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "t_feed")
public class FeedEntity extends BaseEntity{
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ifeed;

    @ManyToOne(fetch = FetchType.LAZY)  //@ManyToOne default -> FetchType.EAGER
    @JoinColumn(name = "iuser", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity userEntity;

    @Column(length = 1000)
    private String contents;

    @Column(length = 30)
    private String location;

    @ToString.Exclude   //toString 할 때 제외하기. oneToMany에 많이 쓰는 편. 양방향(관계설정이 양쪽 entity에 다 있는 경우)이라 무한 루프 발생함.
    @OneToMany(mappedBy = "feedEntity", cascade = CascadeType.PERSIST) //mappedBy = "멤버필드명" -> 안적으면 테이블 늘어남. 영속성 전이 세팅
    private List<FeedPicsEntity> feedPicsEntityList = new ArrayList<>();

//    @ToString.Exclude
//    @OneToMany(mappedBy = "feedEntity")
//    private List<FeedFavEntity> feedFavList = new ArrayList<>();

}
