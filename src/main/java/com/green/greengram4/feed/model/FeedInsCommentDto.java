package com.green.greengram4.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedInsCommentDto {
    @JsonIgnore
    private int ifeedComment;
    @JsonIgnore
    private int iuser;

    @Positive
    private int ifeed;

    @NotEmpty
    private String comment;
}
