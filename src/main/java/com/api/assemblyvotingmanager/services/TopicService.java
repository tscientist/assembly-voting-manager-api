package com.api.assemblyvotingmanager.services;

import com.api.assemblyvotingmanager.models.TopicModel;
import com.api.assemblyvotingmanager.repositories.TopicRepository;
import com.api.assemblyvotingmanager.repositories.VoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class TopicService {
    final TopicRepository topicRepository;
    final VoteRepository voteRepository;

    public TopicService(TopicRepository topicRepository, VoteRepository voteRepository) {
        this.topicRepository = topicRepository;
        this.voteRepository = voteRepository;
    }

    @Transactional
    public TopicModel save(TopicModel topicModel) {
        return topicRepository.save(topicModel);
    }

    public Optional<TopicModel> findById(UUID id) {
        return topicRepository.findById(id);
    }

    public Page<TopicModel> findAll(Pageable pageable) {
        return topicRepository.findAll(pageable);
    }

    public Boolean isApproved(UUID id) {
        var topic = topicRepository.findById(id);

        return true;
    }
}
