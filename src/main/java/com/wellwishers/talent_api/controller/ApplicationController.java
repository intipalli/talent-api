package com.wellwishers.talent_api.controller;

import com.wellwishers.talent_api.model.Application;
import com.wellwishers.talent_api.model.ApplicationJobResponse;
import com.wellwishers.talent_api.model.Job;
import com.wellwishers.talent_api.repository.ApplicationRepository;
import com.wellwishers.talent_api.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @GetMapping
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable int id) {
        Optional<Application> application = applicationRepository.findById(id);
        return application.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<ApplicationJobResponse>> getApplicationsByCandidateId(@PathVariable int candidateId) {
        List<Application> applications = applicationRepository.findByUserId(candidateId);

        if (applications.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<ApplicationJobResponse> response = applications.stream()
                .map(application -> {
                    Optional<Job> jobOpt = jobRepository.findById(application.getJobId());
                    Job job = jobOpt.orElse(null);
                    return new ApplicationJobResponse(application, job);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/job/{jobId}")
    public List<Application> getApplicationsByJobId(@PathVariable int jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    @PostMapping
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        Application createdApplication = applicationRepository.save(application);
        return ResponseEntity.ok(createdApplication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable int id,
            @RequestBody Application applicationDetails) {
        Optional<Application> application = applicationRepository.findById(id);
        if (application.isPresent()) {
            Application existingApplication = application.get();
            existingApplication.setUserId(applicationDetails.getUserId());
            existingApplication.setJobId(applicationDetails.getJobId());
            existingApplication.setDateApplied(applicationDetails.getDateApplied());
            existingApplication.setCoverLetter(applicationDetails.getCoverLetter());
            existingApplication.setCustomResume(applicationDetails.getCustomResume());
            existingApplication.setApplicationStatus(applicationDetails.getApplicationStatus());
            Application updatedApplication = applicationRepository.save(existingApplication);
            return ResponseEntity.ok(updatedApplication);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable("id") int id,
            @RequestBody Map<String, String> updates) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        String applicationStatus = updates.get("applicationStatus");
        application.setApplicationStatus(applicationStatus);
        Application updatedApplication = applicationRepository.save(application);
        return ResponseEntity.ok(updatedApplication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable int id) {
        if (applicationRepository.existsById(id)) {
            applicationRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
