package com.green.greengram4.feed;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram4.CharEncodingConfig;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedInsDto;
import com.green.greengram4.feed.model.FeedSelVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@MockMvcConfig //한글 안 깨지게 해주는 어노테이션 만들어서 사용
@Import(CharEncodingConfig.class)
@WebMvcTest(FeedController.class) //스프링 컨테이너 올려줌. feedController 빈 등록 됨
class FeedControllerTest {

    @Autowired
    private MockMvc mvc;    //가상 신호 전송에 필요 ? 포스트맨 역할
    @MockBean   //컨트롤러에서 필요한 Bean객체에 대해 Mock 형태의 객체를 생성해줌.
    private FeedService service;
    @Autowired
    private ObjectMapper mapper;    //객체를 제이슨 형태의 문자열로. 제이슨을 객체로 변환할 때 사용

    @Test
    void postFeed() throws Exception {
        ResVo result = new ResVo(2);
        //when(service.postFeed(any())).thenReturn(result);
//        given(service.postFeed(any(), any())).willReturn(result);  //when과 같은 효과
        //given - when - then
        //given : 세팅 / when : 실행 / then : 검증
        FeedInsDto dto = new FeedInsDto();

        String json = mapper.writeValueAsString(dto);   //dto객체를 json 형태의 스트링 값으로 변경
        System.out.println(json);
        mvc.perform(        //restAPI 테스트 환경을 만들어주는 역할
                        MockMvcRequestBuilders.post("/api/feed") //post 통신 요청
                                .contentType(MediaType.APPLICATION_JSON)    //헤더 부분. json 형식으로 설정. 없으면 form 데이터 형식이 기본값
                                .content(mapper.writeValueAsString(dto))    //바디 부분. josn 형식의 문자열 데이터 담아줌.
                )
                .andExpect(status().isOk())     //status : 상태값. 통신 응답 결과
                .andExpect(content().string(mapper.writeValueAsString(result))) //기대한 결과값인지 확인하는 메서드
                .andDo(print());    //통신에 결과 출력 ?

        verify(service).postFeed(any(), any());
    }

    @Test
    void getAllFeed() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "2");
        params.add("loginedIuser", "4");
        List<FeedSelVo> result = new ArrayList<>();
        FeedSelVo feedSelVo = new FeedSelVo();
        feedSelVo.setContents("ㅎㅇ");
        result.add(feedSelVo);
        given(service.getAllFeed(any())).willReturn(result);

        mvc.perform(
                        MockMvcRequestBuilders.get("/api/feed")
                                .params(params)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(result)))
                .andDo(print());

        verify(service).getAllFeed(any());
    }

    @Test
    void toggleFav() {
    }

    @Test
    void delFeed() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("ifeed", "1");
        params.add("iuser", "2");

        ResVo result = new ResVo(1);
        given(service.delFeed(any())).willReturn(result);
        mvc.perform(
                        MockMvcRequestBuilders.delete("/api/feed")
                                .params(params)     //쿼리스트링 작성법. params() 없이 직접 쓸 수도 있다.
                )
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(result)))
                .andDo(print());

        verify(service).delFeed(any());
    }
}