package com.green.greengram4.feed;

import com.green.greengram4.common.Const;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)  //스프링 객체화. 테스트에 실행할 클래서 지정. 필요한 것만 빈등록
@Import({FeedService.class})        //서비스만 빈 등록. 
class FeedServiceTest {
    @MockBean   //가상의 객체(가짜)의 주소값 할당
    private FeedMapper feedMapper;
    @MockBean   //서비스가 DI받는 것들 다 @MockBean으로 가상 주소값 받아와야 테스트 가능
    private FeedPicsMapper picsMapper;
    @MockBean   //※ mock : 모조품
    private FeedFavMapper favMapper;
    @MockBean
    private FeedCommentMapper commentMapper;
    @Autowired
    private FeedService service;

    @Test
    void postFeed() {
        when(feedMapper.insFeed(any())).thenReturn(1);  //특정 메서드가 호출되면 1 리턴
        //객체가 특정 상황에서 해야하는 행위 지정
        when(picsMapper.insPic(any())).thenReturn(3);   //any() : 파라미터로 어떠한 값이 와도

        FeedInsDto dto = new FeedInsDto();
        ResVo vo = service.postFeed(dto);

        verify(feedMapper).insFeed(any());  //해당 객체의 메서드 실제로 호출했는지 확인
        verify(picsMapper).insPic(any());   //두번 호출하고 확인하면 에러 발생함
    }

    @Test
    void getAllFeed() {
        FeedSelVo vo1 = new FeedSelVo();
        vo1.setIfeed(1);
        vo1.setContents("1번 피드");
        FeedSelVo vo2 = new FeedSelVo();
        vo2.setIfeed(2);
        vo2.setContents("2번 피드");
        List<FeedSelVo> list = new ArrayList<>();
        list.add(vo1);
        list.add(vo2);

        when(feedMapper.selAllFeed(any())).thenReturn(list);

        FeedSelPicVo pvo1 = new FeedSelPicVo();
        pvo1.setIfeed(1);
        pvo1.setPic("1.jpg");
        FeedSelPicVo pvo2 = new FeedSelPicVo();
        pvo2.setIfeed(1);
        pvo2.setPic("2.jpg");
        FeedSelPicVo pvo3 = new FeedSelPicVo();
        pvo3.setIfeed(2);
        pvo3.setPic("A.jpg");
        FeedSelPicVo pvo4 = new FeedSelPicVo();
        pvo4.setIfeed(2);
        pvo4.setPic("B.jpg");
        List<FeedSelPicVo> picVoList = new ArrayList<>();
        picVoList.add(pvo1);
        picVoList.add(pvo2);
        picVoList.add(pvo3);
        picVoList.add(pvo4);

        List<FeedSelVo>[] lists = new List[2];  //리스트를 아이템으로 가지는 배열 만드는 법
        lists[0] = list;    //리스트보단 배열이 처리속도가 빠르다.
        lists[1] = list;

        when(picsMapper.selPicsByIfeeds(any())).thenReturn(picVoList);

        List<FeedSelCommentVo> commentVoList1 = new ArrayList<>();
        FeedSelCommentVo commentVo1 = new FeedSelCommentVo();
        commentVo1.setIfeedComment(1);
        commentVo1.setWriterIuser(3);
        FeedSelCommentVo commentVo2 = new FeedSelCommentVo();
        commentVo2.setIfeedComment(2);
        commentVo2.setWriterIuser(4);
        FeedSelCommentVo commentVo3 = new FeedSelCommentVo();
        commentVo3.setIfeedComment(3);
        commentVo3.setWriterIuser(5);
        FeedSelCommentVo commentVo4 = new FeedSelCommentVo();
        commentVo4.setIfeedComment(4);
        commentVo4.setWriterIuser(6);
        commentVoList1.add(commentVo1);
        commentVoList1.add(commentVo2);
        commentVoList1.add(commentVo3);
        commentVoList1.add(commentVo4);

        FeedSelCommentVo commentVo5 = new FeedSelCommentVo();
        commentVo4.setIfeedComment(5);
        commentVo4.setWriterIuser(7);
        List<FeedSelCommentVo> commentVoList2 = new ArrayList<>();
        commentVoList2.add(commentVo1);
        commentVoList2.add(commentVo5);

        FeedSelCommentDto commentDto1 = FeedSelCommentDto.builder()
                .ifeed(list.get(0).getIfeed())
                .commentCnt(Const.MAX_COMMENT_COUNT)
                .build();
        when(commentMapper.selFeedCommentAll(commentDto1)).thenReturn(commentVoList1);

        FeedSelCommentDto commentDto2 = FeedSelCommentDto.builder()
                .ifeed(list.get(1).getIfeed())
                .commentCnt(Const.MAX_COMMENT_COUNT)
                .build();
        when(commentMapper.selFeedCommentAll(commentDto2)).thenReturn(commentVoList2);

        FeedSelDto dto = new FeedSelDto();
        List<FeedSelVo> result = service.getAllFeed(dto);

        assertEquals(list, result);

        for (FeedSelPicVo feedSelPicVo : picVoList) {
            assertTrue(result.get(feedSelPicVo.getIfeed() - 1).getPics().contains(feedSelPicVo.getPic()));
        }
        for (FeedSelVo feed : result) {
            System.out.printf("%d번 피드 테스트\n", feed.getIfeed());
            assertEquals(feed.getIfeed() == 1 ? 3 : 2, feed.getComments().size());
            assertEquals(feed.getIfeed() == 1 ? 1 : 0, feed.getIsMoreComment());
            assertEquals(feed.getIfeed() == 1 ? commentVoList1 : commentVoList2, feed.getComments());
            System.out.println("성공");
        }
    }

    @Test
    void toggleFeedFav() {
    }

    @Test
    void delFeed() {
    }
}