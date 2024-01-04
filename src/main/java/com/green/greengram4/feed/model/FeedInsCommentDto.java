package com.green.greengram4.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedInsCommentDto {
    @JsonIgnore
    private int ifeedComment;
    private int ifeed;
    private int iuser;
    private String comment;
}
