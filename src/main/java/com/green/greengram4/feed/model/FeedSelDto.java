package com.green.greengram4.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.greengram4.common.Const;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class FeedSelDto {
    @JsonIgnore
    @Schema(title = "로그인한 유저 pk")
    private long loginedIuser;
    @Schema(title = "프로필 주인 유저pk")
    private long targetIuser;
    @Schema(title = "좋아요 Feed 리스트")
    private int isFavList;
}
