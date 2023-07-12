package com.api.assemblyvotingmanager.controllers;

import com.api.assemblyvotingmanager.dto.TopicDto;
import com.api.assemblyvotingmanager.dto.TopicVoteSessionDto;
import com.api.assemblyvotingmanager.models.TopicModel;
import com.api.assemblyvotingmanager.services.TopicService;
import org.springframework.beans.BeanUtils;
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
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/topic")
public class TopicController {
    final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    public ResponseEntity<Object> saveTopic(@RequestBody @Valid TopicDto topicDto){
        var topicModel = new TopicModel();
        BeanUtils.copyProperties(topicDto, topicModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(topicService.save(topicModel));
    }

    @GetMapping
    public ResponseEntity<Page<TopicModel>> getAllTopics(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(topicService.findAll(pageable));
    }

    @PutMapping
    @RequestMapping("/{id}")
    public ResponseEntity<Object> startTopicVotingSession(@PathVariable(value = "id") UUID id, @RequestBody @Valid TopicVoteSessionDto topicVoteSessionDto){
        Optional<TopicModel> topicModelOptional = topicService.findById(id);

        if (!topicModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topic not found.");
        }

        if (topicModelOptional.get().getSessionStart() != null) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Topic already has a polling session in action or held.");
        }

        var topicModel = new TopicModel();
        BeanUtils.copyProperties(topicModelOptional.get(), topicModel);
        topicModel.setSessionStart(LocalDateTime.now(ZoneId.of("UTC")));

        if (topicVoteSessionDto.getSessionEnd() != null) {
            topicModel.setSessionEnd(topicModel.getSessionStart().plusMinutes(topicVoteSessionDto.getSessionEnd()));
        } else {
            topicModel.setSessionEnd(LocalDateTime.now(ZoneId.of("UTC")).plusMinutes(1));
        }

        return ResponseEntity.status(HttpStatus.OK).body(topicService.save(topicModel));
    }
}
