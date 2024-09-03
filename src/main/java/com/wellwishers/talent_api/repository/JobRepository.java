package com.wellwishers.talent_api.repository;

import com.wellwishers.talent_api.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    List<Job> findByManagerId(Integer managerId);
}
