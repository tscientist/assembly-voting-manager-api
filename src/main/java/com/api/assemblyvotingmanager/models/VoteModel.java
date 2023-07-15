package com.api.assemblyvotingmanager.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_VOTE")
public class VoteModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private UUID topicId;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Column(nullable = false)
    private String cpf;
    @Column(nullable = false, length = 4)
    private String vote;
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTopicId() {
        return topicId;
    }
    public void setTopicId(UUID topicId) {
        this.topicId = topicId;
    }
    public String getVote() {
        return vote;
    }
    public void setVote(String vote) {
        this.vote = vote;
    }
}
