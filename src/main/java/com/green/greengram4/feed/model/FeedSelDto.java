package com.green.greengram4.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.greengram4.common.Const;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class FeedSelDto {
    @JsonIgnore
    @Schema(title = "페이지", defaultValue = "1")
    private int page;
    @JsonIgnore
    @Schema(title = "로그인한 유저 pk")
    private int loginedIuser;
    @Schema(title = "프로필 주인 유저pk")
    private int targetIuser;
    @Schema(title = "좋아요 Feed 리스트")
    private int isFavList;

    @JsonIgnore
    private int startIdx;
    @JsonIgnore
    private int feedCnt = Const.FEED_COUNT_PER_PAGE;

    public void setPage(int page) {
        this.startIdx = (page - 1) * feedCnt;
    }

}
