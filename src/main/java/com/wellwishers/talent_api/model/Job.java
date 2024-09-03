package com.wellwishers.talent_api.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer managerId;

    private String department;

    private Timestamp dateListed;

    private Timestamp dateClosed;

    private String jobTitle;

    @Column(columnDefinition = "TEXT")
    @Lob
    private String jobDescription;

    @Column(columnDefinition = "TEXT")
    @Lob
    private String additionalInformation;

    private String listingStatus;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Timestamp getDateListed() {
        return dateListed;
    }

    public void setDateListed(Timestamp dateListed) {
        this.dateListed = dateListed;
    }

    public Timestamp getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(Timestamp dateClosed) {
        this.dateClosed = dateClosed;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getListingStatus() {
        return listingStatus;
    }

    public void setListingStatus(String listingStatus) {
        this.listingStatus = listingStatus;
    }
}
