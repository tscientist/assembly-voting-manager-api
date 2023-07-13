package com.api.assemblyvotingmanager.dto;

public class TopicVoteResultDto {
    private Integer votesAgainst;
    private Integer votesInFavor;
    private Boolean approved;

    public Integer getVotesAgainst() {
        return votesAgainst;
    }

    public void setVotesAgainst(Integer votesAgainst) {
        this.votesAgainst = votesAgainst;
    }

    public Integer getVotesInFavor() {
        return votesInFavor;
    }

    public void setVotesInFavor(Integer votesInFavor) {
        this.votesInFavor = votesInFavor;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public TopicVoteResultDto(Integer votesInFavor, Integer votesAgainst, Boolean approved) {
        this.votesInFavor = votesInFavor;
        this.votesAgainst = votesAgainst;
        this.approved = approved;
    }
}
