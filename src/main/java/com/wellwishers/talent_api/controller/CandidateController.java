package com.wellwishers.talent_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wellwishers.talent_api.model.Candidate;
import com.wellwishers.talent_api.repository.CandidateRepository;
import java.util.Optional;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepository;

    // GET all candidates
    @GetMapping
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    // GET candidate by user_id
    @GetMapping("/{userId}")
    public ResponseEntity<Candidate> getCandidateByUserId(@PathVariable("userId") Integer userId) {
        Optional<Candidate> candidate = candidateRepository.findByUserId(userId);
        return candidate.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{userId}/resume")
    public ResponseEntity<byte[]> getResume(@PathVariable("userId") Integer userId) {
        Optional<Candidate> candidateOpt = candidateRepository.findByUserId(userId);

        if (candidateOpt.isPresent()) {
            Candidate candidate = candidateOpt.get();
            byte[] resume = candidate.getResume();

            if (resume != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDisposition(ContentDisposition
                        .builder("attachment")
                        .filename("resume.pdf")
                        .build());

                return new ResponseEntity<>(resume, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Candidate> createCandidate(
            @RequestParam Integer userId,
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String address,
            @RequestParam String phone,
            @RequestParam(required = false) MultipartFile resume) {

        Candidate candidate = new Candidate();
        candidate.setUserId(userId);
        candidate.setFullName(fullName);
        candidate.setEmail(email);
        candidate.setAddress(address);
        candidate.setPhone(phone);

        if (resume != null && !resume.isEmpty()) {
            try {
                candidate.setResume(resume.getBytes());
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }

        Candidate savedCandidate = candidateRepository.save(candidate);
        return ResponseEntity.ok(savedCandidate);
    }

    // PUT update an existing candidate with file upload or create a new candidate
    // if not exists
    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateOrCreateCandidate(
            @PathVariable Integer id,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) MultipartFile resume) {

        Optional<Candidate> optionalCandidate = candidateRepository.findByUserId(id);

        if (optionalCandidate.isPresent()) {
            Candidate candidate = optionalCandidate.get();
            updateCandidateFields(candidate, fullName, email, address, phone, resume);
            Candidate updatedCandidate = candidateRepository.save(candidate);
            return ResponseEntity.ok(updatedCandidate);
        } else {
            // If candidate does not exist, create a new one
            Candidate newCandidate = new Candidate();
            newCandidate.setUserId(id);
            updateCandidateFields(newCandidate, fullName, email, address, phone, resume);
            Candidate createdCandidate = candidateRepository.save(newCandidate);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCandidate);
        }
    }

    private void updateCandidateFields(Candidate candidate, String fullName, String email, String address, String phone,
            MultipartFile resume) {
        if (fullName != null)
            candidate.setFullName(fullName);
        if (email != null)
            candidate.setEmail(email);
        if (address != null)
            candidate.setAddress(address);
        if (phone != null)
            candidate.setPhone(phone);
        if (resume != null && !resume.isEmpty()) {
            try {
                candidate.setResume(resume.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Error processing resume file", e);
            }
        }
    }

    // DELETE a candidate
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Integer id) {
        if (candidateRepository.existsById(id)) {
            candidateRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
