package com.api.assemblyvotingmanager.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_TOPIC")
public class TopicModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, length = 255)
    private String title;
    @Column(nullable = false, length = 512)
    private String description;
    @Column()
    private LocalDateTime sessionStart = null;
    @Column()
    private LocalDateTime sessionEnd = null;
    @Column(nullable = false)
    private Integer votesInFavor = 0;
    @Column(nullable = false)
    private Integer votesAgainst = 0;
    @Column(nullable = false)
    private Boolean approved = false;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getSessionStart() {
        return sessionStart;
    }

    public void setSessionStart(LocalDateTime sessionStart) {
        this.sessionStart = sessionStart;
    }

    public LocalDateTime getSessionEnd() {
        return sessionEnd;
    }

    public void setSessionEnd(LocalDateTime sessionEnd) {
        this.sessionEnd = sessionEnd;
    }

    public Integer getVotesInFavor() {
        return votesInFavor;
    }

    public void setVotesInFavor(Integer votesInFavor) {
        this.votesInFavor = votesInFavor;
    }

    public Integer getVotesAgainst() {
        return votesAgainst;
    }

    public void setVotesAgainst(Integer votesAgainst) {
        this.votesAgainst = votesAgainst;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean status) {
        this.approved = status;
    }
}
