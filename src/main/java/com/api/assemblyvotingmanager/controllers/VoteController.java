package com.api.assemblyvotingmanager.controllers;

import com.api.assemblyvotingmanager.dto.VoteDto;
import com.api.assemblyvotingmanager.models.VoteModel;
import com.api.assemblyvotingmanager.services.VoteService;
import org.springframework.beans.BeanUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/vote")
public class VoteController {
    final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<Object> saveVote(@RequestBody @Valid VoteDto voteDto){
        var voteModel = new VoteModel();
        BeanUtils.copyProperties(voteDto, voteModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(voteService.save(voteModel));
    }

//    @GetMapping
//    public ResponseEntity<Page<TopicModel>> getAllVotes(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
//        return ResponseEntity.status(HttpStatus.OK).body(voteService.findAll());
//    }
}
