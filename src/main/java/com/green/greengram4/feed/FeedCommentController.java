package com.green.greengram4.feed;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedInsCommentDto;
import com.green.greengram4.feed.model.FeedSelCommentVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed/comment")
public class FeedCommentController {
    private final FeedCommentService service;

    @PostMapping
    public ResVo postFeedComment(@Valid @RequestBody FeedInsCommentDto dto) {
        return service.postFeedComment(dto);
    }

    @GetMapping
    public List<FeedSelCommentVo> getFeedCommentAll(int ifeed){ //4~999 까지의 레코드만 리턴
        return service.getFeedCommentAll(ifeed);
    }
}
