package com.api.assemblyvotingmanager.services;

import com.api.assemblyvotingmanager.controllers.AwsSqsApplicationController;
import com.api.assemblyvotingmanager.dto.TopicEndSessionMessageDto;
import com.api.assemblyvotingmanager.models.TopicModel;
import com.api.assemblyvotingmanager.models.VoteModel;
import com.api.assemblyvotingmanager.repositories.TopicRepository;
import com.api.assemblyvotingmanager.repositories.VoteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TopicService {
    Logger logger = LoggerFactory.getLogger(AwsSqsApplicationController.class);

    final TopicRepository topicRepository;
    final VoteRepository voteRepository;
    final VoteService voteService;

    public TopicService(TopicRepository topicRepository, VoteRepository voteRepository, VoteService voteService) {
        this.topicRepository = topicRepository;
        this.voteRepository = voteRepository;
        this.voteService = voteService;
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

    public void endVoteSession(UUID topicId) {
        Optional<TopicModel> topic = topicRepository.findById(topicId);
        var topicModel = new TopicModel();
        BeanUtils.copyProperties(topic.get(), topicModel);

        if (topicModel.getApproved() == null) {
            List<VoteModel> votes = voteService.findVotesByTopic(topicId);
            Integer votesInFavor = voteService.getVotes(votes, "Sim");
            Integer votesAgainst = voteService.getVotes(votes, "Não");

            Boolean approved = voteService.isApproved(votesInFavor, votesAgainst);
            topicModel.setVotesInFavor(votesInFavor);
            topicModel.setVotesAgainst(votesAgainst);
            topicModel.setApproved(approved);
            topicRepository.save(topicModel);
        }
    }
}
