package com.api.assemblyvotingmanager.controllers;

import com.api.assemblyvotingmanager.dto.VoteDto;
import com.api.assemblyvotingmanager.models.TopicModel;
import com.api.assemblyvotingmanager.models.VoteModel;
import com.api.assemblyvotingmanager.repositories.VoteRepository;
import com.api.assemblyvotingmanager.services.TopicService;
import com.api.assemblyvotingmanager.services.VoteService;
import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/vote")
public class VoteController {
    final VoteService voteService;
    final TopicService topicService;

    @Autowired
    VoteRepository voteRepository;
    public VoteController(VoteService voteService, TopicService topicService) {
        this.voteService = voteService;
        this.topicService = topicService;
    }

    @PostMapping
    public ResponseEntity<Object> saveVote(@RequestBody @Valid VoteDto voteDto){
        try{
            UUID topicUuid = UUID.fromString(voteDto.getTopicId());
            Optional<TopicModel> topicModelOptional = topicService.findById(topicUuid);

            if (!topicModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topic not found.");
            }

            if (topicModelOptional.get().getApproved() != null) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Voting session for this topic has ended.");
            }

            if (LocalDateTime.now(ZoneId.of("UTC")).isAfter((topicModelOptional.get().getSessionEnd()))) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Voting session for this topic has ended.");
            }

            var voteModel = new VoteModel();
            BeanUtils.copyProperties(voteDto, voteModel);
            voteModel.setTopicId(topicUuid);
            return ResponseEntity.status(HttpStatus.CREATED).body(voteService.save(voteModel));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Not a valid topic id.");
        }
    }

    @GetMapping
    public ResponseEntity<Page<VoteModel>> getAllVotes(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(voteService.findAll(pageable));
    }

//    @GetMapping("/result")
//    public ResponseEntity<List<VoteModel>> getResult(@RequestParam UUID topicId) {
//        return new ResponseEntity<>(voteRepository.findByTopicId(topicId), HttpStatus.OK);
//    }
}
