package com.api.assemblyvotingmanager.services;

import com.api.assemblyvotingmanager.models.TopicModel;
import com.api.assemblyvotingmanager.repositories.TopicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class TopicService {
    final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
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

}
