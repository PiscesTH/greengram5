package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.feed.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedMapper {
    int insFeed(FeedInsProcDto dto);
    List<FeedSelVo> selAllFeed(FeedSelDto dto);
    int delFeedProc(FeedDelDto dto);
    int delFeed(FeedDelDto dto);
}
