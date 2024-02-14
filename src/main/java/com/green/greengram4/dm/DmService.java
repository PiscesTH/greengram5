package com.green.greengram4.dm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.dm.model.*;
import com.green.greengram4.user.UserMapper;
import com.green.greengram4.user.model.UserModel;
import com.green.greengram4.user.model.UserSigninDto;
import com.green.greengram4.user.model.UserSigninProcVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DmService {
    private final DmMapper mapper;
    private final UserMapper userMapper;
    private final ObjectMapper objMapper;

    public List<DmSelMsgAllVo> getMsgAll(DmSelMsgDto dto) {
        return mapper.selDmMsgAll(dto);
    }

    public List<DmSelVo> getDmAll(DmSelDto dto) {
        return mapper.selDmAll(dto);
    }

    public ResVo postDmMsg(DmMsgInsDto dto) {
        int insAffectedRows = mapper.insDmMsg(dto);
        //last msg update
        if(insAffectedRows == 1) {
            int updAffectedRows = mapper.updDmLastMsg(dto);
        }
        LocalDateTime now = LocalDateTime.now(); // 현재 날짜 구하기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 포맷 정의
        String createdAt = now.format(formatter); // 포맷 적용

        //상대방의 firebaseToken값 필요. 나의 pic, iuser값 필요.
        UserModel otherPerson = mapper.selOtherPersonByLoginUser(dto);

        try {

            if(otherPerson.getFirebaseToken() != null) {
                DmMsgPushVo pushVo = new DmMsgPushVo();
                pushVo.setIdm(dto.getIdm());
                pushVo.setSeq(dto.getSeq());
                pushVo.setWriterIuser(dto.getLoginedIuser());
                pushVo.setWriterPic(dto.getLoginedPic());
                pushVo.setMsg(dto.getMsg());
                pushVo.setCreatedAt(createdAt);

                //object to json
                String body = objMapper.writeValueAsString(pushVo);
                log.info("body: {}", body);
                Notification noti = Notification.builder()
                        .setTitle("dm")
                        .setBody(body)
                        .build();

                Message message = Message.builder()
                        .setToken(otherPerson.getFirebaseToken())

                        .setNotification(noti)
                        .build();

                FirebaseMessaging.getInstance().sendAsync(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResVo(dto.getSeq());
    }

    public ResVo delDmMsg(DmDelMsgDto dto) {
        int delResult = mapper.delDmMsg(dto);
        return new ResVo(delResult);
    }

    public DmSelVo postDm(DmInsDto dto) {
        Integer existDmCheck = mapper.checkDmExist(dto);
        if (existDmCheck != null) {
            /*DmSelDto selDto = new DmSelDto();
            selDto.setLoginedIuser(dto.getLoginedIuser());
            List<DmSelVo> voList = mapper.selDmAll(selDto);
            for (DmSelVo vo : voList) {
                if (vo.getOtherPersonIuser() == existDmCheck) {
                    return vo;
                }
            }*/return null;
        }
        int insDmResult = mapper.insDm(dto);
        /*if (insDmResult == 0) {
            return null; //dm방 생성 실패 처리는 ?
        }*/
        int insDmUser = mapper.insDmUser(dto);
        UserSigninProcVo procVo = userMapper.selLoginInfoByUid(UserSigninDto.builder()
                .iuser(dto.getOtherPersonIuser())
                .build());
        return DmSelVo.builder()
                .idm(dto.getIdm())
                .otherPersonIuser(dto.getOtherPersonIuser())
                .otherPersonNm(procVo.getNm())
                .otherPersonPic(procVo.getPic())
                .build();
    }
}
