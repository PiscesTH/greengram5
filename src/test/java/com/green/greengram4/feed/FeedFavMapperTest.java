package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedToggleFavDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MybatisTest    //Mybatis를 테스트 한다. DAO 관련 xml, interface만 빈등록 해준다.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//기존 데이터 베이스로 테스트 할 때 사용. 없으면 H2 데이터 베이스 사용한다.

class FeedFavMapperTest {
    @Autowired
    private FeedFavMapper mapper;

    @Test
    @DisplayName("fav insert test")
    public void insFeedFav() {
        FeedToggleFavDto dto = FeedToggleFavDto.builder()
                .ifeed(20)
                .iuser(10)
                .build();
        List<FeedToggleFavDto> selResult = mapper.selFeedFavForTest(dto);
        assertEquals(0, selResult.size(), "첫 번째 select 테스트");
        int insResult = mapper.insFeedFav(dto);
        assertEquals(1, insResult, "첫 번째 insert 테스트"); //테스트 실패하면 나오는 문자열 추가 가능
        selResult = mapper.selFeedFavForTest(dto);
        assertEquals(1, selResult.size(), "두 번째 select 테스트");
    }

    @Test
    @DisplayName("fav delete test")
    public void delFeedFav() {
        FeedToggleFavDto dto = FeedToggleFavDto.builder()
                .ifeed(2)
                .iuser(1)
                .build();

        int delResult = mapper.delFeedFav(dto);
        assertEquals(1, delResult);
        int delResult2 = mapper.delFeedFav(dto);
        assertEquals(0, delResult2);
        List<FeedToggleFavDto> selResult = mapper.selFeedFavForTest(dto);
        assertEquals(0, selResult.size());
    }

    @Test
    @DisplayName("fav delete all test")
    public void delFeedFavAll() {
        final int IFEED = 1;
        FeedToggleFavDto dto = FeedToggleFavDto.builder()
                .ifeed(IFEED)
                .build();

        List<FeedToggleFavDto> selResult = mapper.selFeedFavForTest(dto);
        int delAllResult = mapper.delFeedFavAll(IFEED);
        assertEquals(selResult.size(), delAllResult);

        selResult = mapper.selFeedFavForTest(dto);
        assertEquals(0, selResult.size());
    }
}