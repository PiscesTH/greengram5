package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedInsCommentDto;
import com.green.greengram4.feed.model.FeedSelCommentDto;
import com.green.greengram4.feed.model.FeedSelCommentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedCommentMapper {
    int insFeedComment(FeedInsCommentDto dto);
    List<FeedSelCommentVo> selFeedCommentAll(FeedSelCommentDto dto);
}
