package com.green.greengram4.feed;

import com.green.greengram4.BaseIntegrationTest;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedInsDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FeedIntegrationTest extends BaseIntegrationTest {

    @Test
    public void postFeed() throws Exception {
        FeedInsDto dto = new FeedInsDto();
        dto.setIuser(7);
        dto.setContents("통합 테스트 중");
        dto.setLocation("그린컴퓨터학원");
        List<String> pics = new ArrayList<>();
        pics.add("https://images.unsplash.com/photo-1682686581427-7c80ab60e3f3?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        pics.add("https://images.unsplash.com/photo-1704024213027-c3555991bc63?q=80&w=1935&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
//        dto.setPics(pics);

        String json = om.writeValueAsString(dto);   //dto객체를 json 형태의 스트링 값으로 변경
        MvcResult mr = mvc.perform(        //restAPI 테스트 환경을 만들어주는 역할
                        MockMvcRequestBuilders.post("/api/feed") //post 통신 요청
                                .contentType(MediaType.APPLICATION_JSON)    //헤더 부분. json 형식으로 설정. 없으면 form 데이터 형식이 기본값
                                .content(json)    //바디 부분. josn 형식의 문자열 데이터 담아줌.
                )
                .andExpect(status().isOk())     //status : 상태값. 통신 응답 결과
                //.andDo(print())    //통신 결과 출력
                .andReturn();

        String contents = mr.getResponse().getContentAsString();
        //ResVo resVo = om.convertValue(contents, ResVo.class);
        ResVo resVo = om.readValue(contents, ResVo.class);

        assertNotEquals(0, resVo.getResult());
    }

    @Test
    @Rollback(value = false)
    public void delFeed() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("ifeed", "117");
        params.add("iuser", "7");
        MvcResult mr = mvc.perform(
                        //MockMvcRequestBuilders.delete("/api/feed?ifeed={ifeed}&iuser={iuser}","117","7")  //쿼리스트링 작성법
                        MockMvcRequestBuilders.delete("/api/feed")
                                .params(params)
                )
                .andExpect(status().isOk())
                .andReturn();

        String contents = mr.getResponse().getContentAsString();
        ResVo resVo = om.readValue(contents, ResVo.class);
        assertEquals(1, resVo.getResult());
    }
}
