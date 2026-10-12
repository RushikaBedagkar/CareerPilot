package com.careerpilot.dto;

public class JobResponse {

    private Long id;
    private String title;
    private String company;
    private String location;
    private String jobType;
    private String description;
    private String skills;
    private String salary;
    private String applicationUrl;

    public JobResponse() {
    }

    public JobResponse(Long id,
                       String title,
                       String company,
                       String location,
                       String jobType,
                       String description,
                       String skills,
                       String salary,
                       String applicationUrl) {

        this.id = id;
        this.title = title;
        this.company = company;
        this.location = location;
        this.jobType = jobType;
        this.description = description;
        this.skills = skills;
        this.salary = salary;
        this.applicationUrl = applicationUrl;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getJobType() {
        return jobType;
    }

    public String getDescription() {
        return description;
    }

    public String getSkills() {
        return skills;
    }

    public String getSalary() {
        return salary;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }
}