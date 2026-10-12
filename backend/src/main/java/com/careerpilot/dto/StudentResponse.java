package com.careerpilot.dto;

public class StudentResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String college;
    private String degree;
    private Integer graduationYear;
    private Double cgpa;
    private String githubUrl;
    private String linkedinUrl;
    private String resumeUrl;

    public StudentResponse() {
    }

    public StudentResponse(Long id, String name, String email,
                           String phone, String college, String degree,
                           Integer graduationYear, Double cgpa,
                           String githubUrl, String linkedinUrl,
                           String resumeUrl) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.college = college;
        this.degree = degree;
        this.graduationYear = graduationYear;
        this.cgpa = cgpa;
        this.githubUrl = githubUrl;
        this.linkedinUrl = linkedinUrl;
        this.resumeUrl = resumeUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCollege() {
        return college;
    }

    public String getDegree() {
        return degree;
    }

    public Integer getGraduationYear() {
        return graduationYear;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }
}