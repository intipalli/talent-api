package com.wellwishers.talent_api.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "job_id")
    private int jobId;

    @Column(name = "date_applied")
    private Timestamp dateApplied;

    @Column(name = "cover_letter")
    private String coverLetter;

    @Lob
    @Column(name = "custom_resume", columnDefinition = "LONGBLOB")
    private byte[] customResume;

    @Column(name = "application_status")
    private String applicationStatus;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public Timestamp getDateApplied() {
        return dateApplied;
    }

    public void setDateApplied(Timestamp dateApplied) {
        this.dateApplied = dateApplied;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public byte[] getCustomResume() {
        return customResume;
    }

    public void setCustomResume(byte[] customResume) {
        this.customResume = customResume;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}
