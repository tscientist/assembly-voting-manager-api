package com.api.assemblyvotingmanager.services;

import com.api.assemblyvotingmanager.models.VoteModel;
import com.api.assemblyvotingmanager.repositories.VoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service

public class VoteService {
    final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Transactional
    public VoteModel save(VoteModel voteModel) {
        return voteRepository.save(voteModel);
    }

    public Page<VoteModel> findAll(Pageable pageable) {
        return voteRepository.findAll(pageable);
    }

    public List<VoteModel> findVotesByTopic(UUID topicId) {
        return voteRepository.findAll();
    }
}
