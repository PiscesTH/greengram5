package com.green.greengram4.dm.model;

import lombok.Data;


@Data
public class DmSelMsgAllVo {
    private int seq;
    private int writerIuser;
    private String writerPic;
    private String msg;
    private String createdAt;
}
