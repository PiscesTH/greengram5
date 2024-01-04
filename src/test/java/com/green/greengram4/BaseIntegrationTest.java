package com.green.greengram4;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
//@Import(CharEncodingConfig.class) //기존에 작성한 @MockMvcConfig 로 대체 가능. 둘 중 하나만 사용 하면 됨.
@MockMvcConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //통합테스트 어노테이션. 포트번호 랜덤
@AutoConfigureMockMvc
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BaseIntegrationTest {  //통합테스트 - 컨트롤러, 서비스, 맵퍼 전체 테스트. 포스트맨 보내보는 느낌
                                    //통합테스트에 필요한 공통 데이터 작성해놓고 상속해서 사용하기 위해 작성된 클래스
    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper om;
}
