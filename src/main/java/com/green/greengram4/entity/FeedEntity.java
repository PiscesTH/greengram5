package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne
    @JoinColumn(name = "iuser", referencedColumnName = "iuser", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity userEntity;

    @Column(length = 1000)
    private String contents;

    @Column(length = 30)
    private String location;

    @ToString.Exclude   //toString 할 때 제외하기. oneToMany에 많이 쓰는 편.
    @OneToMany(mappedBy = "feedEntity", cascade = CascadeType.PERSIST) //mappedBy = "멤버필드명" -> 안적으면 테이블 늘어남. 영속성 전이 세팅
    private List<FeedPicsEntity> feedPicsEntityList = new ArrayList<>();
}
