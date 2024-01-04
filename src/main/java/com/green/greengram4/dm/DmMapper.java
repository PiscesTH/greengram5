package com.green.greengram4.dm;

import com.green.greengram4.dm.model.*;
import com.green.greengram4.user.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DmMapper {
    int insDmMsg(DmMsgInsDto dto);
    List<DmSelMsgAllVo> selDmMsgAll(DmSelMsgDto dto);
    List<DmSelVo> selDmAll(DmSelDto dto);
    int delDmMsg(DmDelMsgDto dto);
    int insDm(DmInsDto dto);
    int insDmUser(DmInsDto dto);
    Integer checkDmExist(DmInsDto dto);
    int updDmLastMsg(DmMsgInsDto dto);

    UserEntity selOtherPersonByLoginUser(DmMsgInsDto dto);
}
