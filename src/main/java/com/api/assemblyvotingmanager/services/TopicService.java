package com.api.assemblyvotingmanager.services;

import com.api.assemblyvotingmanager.models.TopicModel;
import com.api.assemblyvotingmanager.repositories.TopicRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
