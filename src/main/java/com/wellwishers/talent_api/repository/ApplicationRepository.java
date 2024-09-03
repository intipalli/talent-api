package com.wellwishers.talent_api.repository;

import com.wellwishers.talent_api.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    List<Application> findByJobId(int jobId);

    List<Application> findByUserId(int userId);

    List<Application> findByApplicationStatus(String applicationStatus);
}
