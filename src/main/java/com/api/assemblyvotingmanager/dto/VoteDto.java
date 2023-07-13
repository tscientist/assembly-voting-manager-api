package com.api.assemblyvotingmanager.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class VoteDto {
    @NotBlank
    @Size(max = 255)
    private String topicId;
    @NotBlank
    @Size(max = 255)
    private String userId;

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    @NotBlank
    @Size(max = 4)
    private String vote;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}
