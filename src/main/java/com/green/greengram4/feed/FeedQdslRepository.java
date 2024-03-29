package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.FeedFavEntity;
import com.green.greengram4.entity.FeedPicsEntity;
import com.green.greengram4.feed.model.FeedSelDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeedQdslRepository {
    List<FeedEntity> selFeedAll(FeedSelDto dto, Pageable pageable);
    List<FeedPicsEntity> selFeedPicsAll(List<FeedEntity> feedEntityList);
    List<FeedFavEntity> selFeedMyFavAll(List<FeedEntity> feedEntityList, long loginedIuser);
}
