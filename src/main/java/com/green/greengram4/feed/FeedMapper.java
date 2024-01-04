package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedInsProcDto;
import com.green.greengram4.feed.model.FeedSelDto;
import com.green.greengram4.feed.model.FeedSelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedMapper {
    int insFeed(FeedInsProcDto dto);
    List<FeedSelVo> selAllFeed(FeedSelDto dto);
    int delFeedProc(FeedDelDto dto);
    int delFeed(FeedDelDto dto);
}
