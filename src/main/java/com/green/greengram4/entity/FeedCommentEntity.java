package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_feed_comment")
public class FeedCommentEntity extends BaseEntity {
    @Id
    @Column(name = "ifeed_comment", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ifeedComment;

    @ManyToOne//(fetch = FetchType.EAGER) -> 기본값. (fetch = FetchType.LAZY) -> 지연 로딩
    @JoinColumn(name = "ifeed", nullable = false)
    private FeedEntity feedEntity;

    @ManyToOne
    @JoinColumn(name = "iuser", nullable = false)
    private UserEntity userEntity;

    @Column(length = 1000)
    private String comment;
}
