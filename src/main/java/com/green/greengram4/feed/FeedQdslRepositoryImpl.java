package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.FeedFavEntity;
import com.green.greengram4.entity.FeedPicsEntity;
import com.green.greengram4.feed.model.FeedSelDto;
import com.green.greengram4.security.AuthenticationFacade;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.green.greengram4.entity.QFeedEntity.feedEntity;
import static com.green.greengram4.entity.QFeedFavEntity.feedFavEntity;
import static com.green.greengram4.entity.QFeedPicsEntity.feedPicsEntity;

@Slf4j
@RequiredArgsConstructor
public class FeedQdslRepositoryImpl implements FeedQdslRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public List<FeedEntity> selFeedAll(FeedSelDto dto, Pageable pageable) {
        dto.setLoginedIuser(authenticationFacade.getLoginUserPk());
        JPAQuery<FeedEntity> query = jpaQueryFactory.selectFrom(feedEntity) //한 줄로 대체 가능 <- select 와 from 에 같은 내용이 들어갈 때
//                .select(feedEntity)
//                .from(feedEntity)
                .join(feedEntity.userEntity).fetchJoin()    //query dsl inner join 사용법. .fetchJoin() 없고 LAZY 일 땐 의미 없다 ?
                //.fetchJoin() 없고 EAGER 일 땐 데이터 사용 안해도 join으로 데이터 가져온다. 근데 왜 N+1 ?
                .orderBy(feedEntity.ifeed.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
//                .fetch();   //fetch 이후에는 수정 불가.
        if (dto.getIsFavList() == 1) {
            query.join(feedFavEntity).on(   //join(feedEntity.userEntity) -> feedEntity.userEntity : 아래와 비슷한 연관관계 자동으로 설정해줌
                    feedEntity.ifeed.eq(feedFavEntity.feedEntity.ifeed),
                    feedFavEntity.userEntity.iuser.eq(dto.getLoginedIuser())); //, -> and
        } else {
            query.where(whereTargetUser(dto.getTargetIuser()));    //.where(whereTargetUser(B), whereTargetUser(A)) where절 and 조건 만드는 법
        }

        return query.fetch();
    }

    @Override
    public List<FeedPicsEntity> selFeedPicsAll(List<FeedEntity> feedEntityList) {
        return jpaQueryFactory.select(Projections.fields(FeedPicsEntity.class,  //Projections.fields : 필요한 데이터만 들고 올 때 사용 / 리턴타입 클래스 적어주기
                feedPicsEntity.feedEntity, feedPicsEntity.pic))
                .from(feedPicsEntity)
                .where(feedPicsEntity.feedEntity.in(feedEntityList))
                .fetch();
    }

    @Override
    public List<FeedFavEntity> selFeedMyFavAll(List<FeedEntity> feedEntityList, long loginedIuser) {
        return jpaQueryFactory.select(Projections.fields(FeedFavEntity.class, feedFavEntity.feedEntity))    //feedFavEntity.feedEntity.ifeed까지 적을 필요 없음.
                .from(feedFavEntity)
                .where(feedFavEntity.feedEntity.in(feedEntityList), feedFavEntity.userEntity.iuser.eq(loginedIuser))
                .fetch();
    }

    private BooleanExpression whereTargetUser(long targetIuser) {
        return targetIuser == 0 ? null : feedEntity.userEntity.iuser.eq(targetIuser);
        //where절에 들어갈 때 null이면 where절 빠짐.
    }
}
