package com.green.greengram4.feed;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedInsCommentDto;
import com.green.greengram4.feed.model.FeedSelCommentDto;
import com.green.greengram4.feed.model.FeedSelCommentVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedCommentService {
    private final FeedCommentMapper commentMapper;

    public ResVo postFeedComment(FeedInsCommentDto dto) {
        int insResult = commentMapper.insFeedComment(dto);
        if (insResult == 0) {
            return new ResVo(insResult);
        }
        return new ResVo(dto.getIfeedComment());
    }

    public List<FeedSelCommentVo> getFeedCommentAll(int ifeed){
        FeedSelCommentDto dto = FeedSelCommentDto.builder()
                .startIdx(3)
                .commentCnt(999)
                .ifeed(ifeed)
                .build();
        return commentMapper.selFeedCommentAll(dto);
    }
}
