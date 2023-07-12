package com.api.assemblyvotingmanager.repositories;

import com.api.assemblyvotingmanager.models.VoteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VoteRepository extends JpaRepository<VoteModel, UUID> {
}
