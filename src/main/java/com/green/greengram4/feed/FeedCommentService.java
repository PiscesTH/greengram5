package com.green.greengram4.feed;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.exception.FeedErrorCode;
import com.green.greengram4.exception.RestApiException;
import com.green.greengram4.feed.model.FeedInsCommentDto;
import com.green.greengram4.feed.model.FeedSelCommentDto;
import com.green.greengram4.feed.model.FeedSelCommentVo;
import com.green.greengram4.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedCommentService {
    private final FeedCommentMapper commentMapper;
    private final AuthenticationFacade authenticationFacade;

    public ResVo postFeedComment(FeedInsCommentDto dto) {
/*        if (dto.getIfeed() == 0 || !StringUtils.hasText(dto.getComment())) {
            throw new RestApiException(FeedErrorCode.IMPOSSIBLE_REG_COMMENT);
        }*/

        dto.setIuser(authenticationFacade.getLoginUserPk());
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
