package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<FeedEntity, Long> {

}
