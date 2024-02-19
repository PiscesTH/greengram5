package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<FeedEntity, Long> {
    @EntityGraph(attributePaths = {"userEntity"})   //그래프 탐색으로 가져올 데이터 join으로 처리 ? -> N+1 해결 방법.
                                                    // select할 때 join으로 데이터 같이 가져오게 됨.
    List<FeedEntity> findAllByUserEntityOrderByIfeedDesc(UserEntity userEntity, Pageable pageable);
    //멤버필드명 적어야 함.
}
