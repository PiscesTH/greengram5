package com.green.greengram4.feed;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.feed.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
@Tag(name = "피드 API", description = "피드 관련 처리")
public class FeedController {
    private final FeedService service;

    @Operation(summary = "피드 등록")
    @PostMapping
    public FeedInsPicDto postFeed(@RequestPart(required = false) List<MultipartFile> pics,@RequestPart FeedInsDto dto) {
//        log.info("pics : {}", pics);    //사진 기본용량 1mb
        log.info("dto : {}", dto);
        return service.postFeed(pics, dto);
    }


    @Operation(summary = "전체 피드 조회")
    @GetMapping
    public List<FeedSelVo> getAllFeed(FeedSelDto dto, @PageableDefault(page = 1, size = 20) Pageable pageable) {    //Pageable : 페이징 처리에 사용
        return service.getAllFeed(dto, pageable);
    }

    @Operation(summary = "좋아요 처리", description = "좋아요 취소 - 0, 등록 - 1")
    @GetMapping("/fav")
    public ResVo toggleFav(FeedToggleFavDto dto){
        return service.toggleFeedFav(dto);
    }

    @DeleteMapping
    public ResVo delFeed(FeedDelDto dto){
        return service.delFeed(dto);
    }
}
