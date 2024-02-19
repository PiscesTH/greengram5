package com.green.greengram4.feed.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedSelVo {
    private int ifeed;
    private String contents;
    private String location;
    private String createdAt;
    private int writerIuser;
    private String writerNm;
    private String writerPic;
    private List<String> pics = new ArrayList<>();
    private int isFav;  //1 : 좋아요 있음,  0: 좋아요 없음
    private List<FeedSelCommentVo> comments;
    private int isMoreComment; //0: 댓글 더 없음, 1:댓글 더 있음
}
