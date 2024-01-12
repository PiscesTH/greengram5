package com.green.greengram4.feed.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class FeedInsPicDto {
    private int ifeed;
    private List<MultipartFile> pics;
}
