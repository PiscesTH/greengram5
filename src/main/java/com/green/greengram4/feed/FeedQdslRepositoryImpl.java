package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.FeedPicsEntity;
import com.green.greengram4.feed.model.FeedSelVo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.green.greengram4.entity.QFeedEntity.feedEntity;

@Slf4j
@RequiredArgsConstructor
public class FeedQdslRepositoryImpl implements FeedQdslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FeedSelVo> selFeedAll(long loginedIuser, long targetIuser, Pageable pageable) {
        List<FeedEntity> list = jpaQueryFactory.select(feedEntity)
                .from(feedEntity)
                .where(whereTargetUser(targetIuser))    //.where(whereTargetUser(B), whereTargetUser(A)) where절 and 조건 만드는 법
                .orderBy(feedEntity.ifeed.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return list.stream().map(item -> FeedSelVo.builder()
                        .ifeed(item.getIfeed().intValue())
                        .contents(item.getContents())
                        .location(item.getLocation())
                        .createdAt(item.getCreatedAt().toString())
                        .writerIuser(item.getUserEntity().getIuser().intValue())
                        .writerNm(item.getUserEntity().getNm())
                        .writerPic(item.getUserEntity().getPic())
                        .pics(item.getFeedPicsEntityList().stream().map(FeedPicsEntity::getPic).toList())
                        .build()
                ).toList();
    }
    private BooleanExpression whereTargetUser(long targetIuser) {
        return targetIuser == 0 ? null : feedEntity.userEntity.iuser.eq(targetIuser);
        //where절에 들어갈 때 null이면 where절 빠짐.
    }
}
