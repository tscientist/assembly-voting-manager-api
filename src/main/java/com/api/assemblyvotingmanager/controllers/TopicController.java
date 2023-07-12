package com.api.assemblyvotingmanager.controllers;

import com.api.assemblyvotingmanager.dto.TopicDto;
import com.api.assemblyvotingmanager.models.TopicModel;
import com.api.assemblyvotingmanager.services.TopicService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/topic")
public class TopicController {
    final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid TopicDto topicDto){
        var topicModel = new TopicModel();
        BeanUtils.copyProperties(topicDto, topicModel);
        topicModel.setSessionStart(LocalDateTime.now(ZoneId.of("UTC")));
        if (topicDto.getSessionEnd() != null) {
            topicModel.setSessionEnd(topicModel.getSessionStart().plusMinutes(topicDto.getSessionEnd()));
        } else {
            topicModel.setSessionEnd(LocalDateTime.now(ZoneId.of("UTC")).plusMinutes(1));
        }
//        topicModel.setVotesAgainst(0);
//        topicModel.setVotesInFavor(0);
//        topicModel.setApproved(false);
        return ResponseEntity.status(HttpStatus.CREATED).body(topicService.save(topicModel));
    }
}
