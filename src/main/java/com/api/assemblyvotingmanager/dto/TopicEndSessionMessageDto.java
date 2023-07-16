package com.api.assemblyvotingmanager.dto;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class TopicEndSessionMessageDto {
    @NotBlank
    private UUID topicId;

    @NotBlank
    private Integer sessionEnd;

    public UUID getTopicId() {
        return topicId;
    }

    public void setTopicId(UUID topicId) {
        this.topicId = topicId;
    }

    public Integer getSessionEnd() {
        return sessionEnd;
    }

    public void setSessionEnd(Integer sessionEnd) {
        this.sessionEnd = sessionEnd;
    }
}
