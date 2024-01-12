package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedInsPicDto;
import com.green.greengram4.feed.model.FeedSelPicVo;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MybatisTest    //Mybatis를 테스트 한다. DAO 관련 xml, interface만 빈등록 해준다.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeedPicsMapperTest {
    @Autowired
    private FeedPicsMapper picsMapper;

    @Test
    void insPic() {
        FeedInsPicDto dto = FeedInsPicDto.builder()
                .ifeed(115)
                .pics(new ArrayList<>())
                .build();
//        dto.getPics().add("사진 테스트");
//        dto.getPics().add("사진 테스트22");
        List<Integer> ifeeds = new ArrayList<>();
        ifeeds.add(dto.getIfeed());
        List<FeedSelPicVo> beforeList = picsMapper.selPicsByIfeeds(ifeeds);
        int insResult = picsMapper.insPic(dto);
        assertEquals(dto.getPics().size(), insResult);
        List<FeedSelPicVo> afterList = picsMapper.selPicsByIfeeds(ifeeds);
        for (int i = 0; i < dto.getPics().size(); i++) {
            assertEquals(dto.getPics().get(i),
                    afterList.get(beforeList.size() + i).getPic());
        }
    }

    @Test
    void selPicsByIfeeds() {
        List<Integer> ifeeds = new ArrayList<>();
        ifeeds.add(115);
        List<FeedSelPicVo> list = picsMapper.selPicsByIfeeds(ifeeds);
        assertEquals(1, list.size());
    }

    @Test
    void delFeedPics() {
        List<Integer> ifeeds = new ArrayList<>();
        ifeeds.add(1);
        List<FeedSelPicVo> list = picsMapper.selPicsByIfeeds(ifeeds);
        int delResult = picsMapper.delFeedPics(ifeeds.get(0));
        assertEquals(list.size(), delResult);
    }
}