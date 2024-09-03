package com.wellwishers.talent_api.model;

public class ApplicationJobResponse {

    private Application application;
    private Job job;

    public ApplicationJobResponse(Application application, Job job) {
        this.application = application;
        this.job = job;
    }

    // Getters and Setters
    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
