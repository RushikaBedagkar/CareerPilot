package com.careerpilot.dto;

public class ApplicationResponse {

    private Long id;
    private Long jobId;
    private String studentEmail;
    private String status;
    private String appliedDate;

    public ApplicationResponse() {
    }

    public ApplicationResponse(
            Long id,
            Long jobId,
            String studentEmail,
            String status,
            String appliedDate) {

        this.id = id;
        this.jobId = jobId;
        this.studentEmail = studentEmail;
        this.status = status;
        this.appliedDate = appliedDate;
    }

    public Long getId() {
        return id;
    }

    public Long getJobId() {
        return jobId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getStatus() {
        return status;
    }

    public String getAppliedDate() {
        return appliedDate;
    }
}