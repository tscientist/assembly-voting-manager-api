package com.api.assemblyvotingmanager.services;

import br.com.caelum.stella.validation.CPFValidator;
import com.api.assemblyvotingmanager.models.VoteModel;
import com.api.assemblyvotingmanager.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service

public class VoteService {
    @Autowired
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
        try  {
            List<VoteModel> votes = voteRepository.findByTopicId(topicId);
            return votes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer getVotes(List<VoteModel> votes, String voteOption) {
        Integer totalVotes = 0;

        for (VoteModel vote : votes) {
            if (vote.getVote().equals(voteOption)) {
                totalVotes++;
            }
        }

        return totalVotes;
    }

    public Boolean isApproved(Integer votesInFavor, Integer votesAgainst) {
        if (votesInFavor > votesAgainst) {
            return true;
        }
        return false;
    }

    public Boolean validateCpf(String cpf) {
        CPFValidator cpfValidator = new CPFValidator();

        try {
            cpfValidator.assertValid(cpf);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean userAlreadyVoted(String cpf, UUID topicId) {
        if (voteRepository.findAllVotesByCpf(cpf, topicId).isEmpty()) {
            return false;
        }
        return true;
    }
}
