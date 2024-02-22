package com.green.greengram4.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedSelCommentVo {
    private int ifeedComment;
    private String comment;
    private String createdAt;
    private int writerIuser;
    private String writerNm;
    private String writerPic;
    @JsonIgnore
    private long ifeed;
}
