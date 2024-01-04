package com.green.greengram4.feed.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedInsProcDto {
    private int iuser;
    private String contents;
    private String location;
    private int ifeed;
}
