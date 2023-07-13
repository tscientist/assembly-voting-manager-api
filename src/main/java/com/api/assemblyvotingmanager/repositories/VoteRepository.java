package com.api.assemblyvotingmanager.repositories;

import com.api.assemblyvotingmanager.models.VoteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<VoteModel, UUID> {
    @Query("SELECT r FROM VoteModel r where r.topicId = :topicId")
    List<VoteModel> findByTopicId(UUID topicId);
}
