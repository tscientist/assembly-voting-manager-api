package com.api.assemblyvotingmanager.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class VoteDto {
    @NotBlank
    @Size(max = 255)
    private String topicId;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @NotBlank
    @Size(max = 255)
    private String cpf;
    @NotBlank
    @Size(max = 4)
    private String vote;


    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

}
