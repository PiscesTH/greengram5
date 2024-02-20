package com.green.greengram4.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class FeedFavIds implements Serializable {
    private Long ifeed;
    private Long iuser;
}
