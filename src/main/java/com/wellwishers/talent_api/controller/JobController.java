package com.wellwishers.talent_api.controller;

import com.wellwishers.talent_api.model.Job;
import com.wellwishers.talent_api.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    // GET all jobs
    @GetMapping
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // GET job by ID
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Integer id) {
        Optional<Job> job = jobRepository.findById(id);
        return job.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET jobs by manager ID
    @GetMapping("/manager/{managerId}")
    public List<Job> getJobsByManagerId(@PathVariable Integer managerId) {
        return jobRepository.findByManagerId(managerId);
    }

    // POST create a new job
    @PostMapping
    public Job createJob(@RequestBody Job job) {
        return jobRepository.save(job);
    }

    // PUT update an existing job
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Integer id, @RequestBody Job jobDetails) {
        Optional<Job> job = jobRepository.findById(id);

        if (job.isPresent()) {
            Job updatedJob = job.get();
            updatedJob.setManagerId(jobDetails.getManagerId());
            updatedJob.setDepartment(jobDetails.getDepartment());
            updatedJob.setDateListed(jobDetails.getDateListed());
            updatedJob.setDateClosed(jobDetails.getDateClosed());
            updatedJob.setJobTitle(jobDetails.getJobTitle());
            updatedJob.setJobDescription(jobDetails.getJobDescription());
            updatedJob.setAdditionalInformation(jobDetails.getAdditionalInformation());
            updatedJob.setListingStatus(jobDetails.getListingStatus());

            Job savedJob = jobRepository.save(updatedJob);
            return ResponseEntity.ok(savedJob);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE a job
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Integer id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<Job> closeJob(@PathVariable Integer id) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isPresent()) {
            Job job = optionalJob.get();
            job.setListingStatus("CLOSED");
            job.setDateClosed(new Timestamp(System.currentTimeMillis()));
            Job updatedJob = jobRepository.save(job);
            return ResponseEntity.ok(updatedJob);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
