package com.wellwishers.talent_api.repository;

import com.wellwishers.talent_api.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
    Optional<Candidate> findByUserId(Integer userId);
}
