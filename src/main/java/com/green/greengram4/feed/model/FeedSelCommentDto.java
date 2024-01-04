package com.green.greengram4.feed.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedSelCommentDto {
    private int ifeed;
    private int startIdx;
    private int commentCnt;
}
