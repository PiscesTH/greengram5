package com.green.greengram4.user;

import com.green.greengram4.user.model.UserFollowDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFollowMapper {
    int insFollow(UserFollowDto dto);
    int delFollow(UserFollowDto dto);

}
