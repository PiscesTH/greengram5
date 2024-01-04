package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedInsPicDto;
import com.green.greengram4.feed.model.FeedSelPicVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedPicsMapper {
    int insPic(FeedInsPicDto dto);
    List<FeedSelPicVo> selPicsByIfeeds(List<Integer> ifeeds);
    int delFeedPics(int ifeed);
}
