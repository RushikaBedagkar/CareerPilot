package com.careerpilot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String name;

   @Column(unique = true, nullable = false)
   private String email;

   private String phone;

   private String college;

   private String degree;

   private Integer graduationYear;

   private Double cgpa;

   private String githubUrl;

   private String linkedinUrl;

   private String resumeUrl;

   public Student() {
   }

   public Student(String name, String email, String phone,
                  String college, String degree,
                  Integer graduationYear, Double cgpa,
                  String githubUrl, String linkedinUrl,
                  String resumeUrl) {

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

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getCollege() {
      return college;
   }

   public void setCollege(String college) {
      this.college = college;
   }

   public String getDegree() {
      return degree;
   }

   public void setDegree(String degree) {
      this.degree = degree;
   }

   public Integer getGraduationYear() {
      return graduationYear;
   }

   public void setGraduationYear(Integer graduationYear) {
      this.graduationYear = graduationYear;
   }

   public Double getCgpa() {
      return cgpa;
   }

   public void setCgpa(Double cgpa) {
      this.cgpa = cgpa;
   }

   public String getGithubUrl() {
      return githubUrl;
   }

   public void setGithubUrl(String githubUrl) {
      this.githubUrl = githubUrl;
   }

   public String getLinkedinUrl() {
      return linkedinUrl;
   }

   public void setLinkedinUrl(String linkedinUrl) {
      this.linkedinUrl = linkedinUrl;
   }

   public String getResumeUrl() {
      return resumeUrl;
   }

   public void setResumeUrl(String resumeUrl) {
      this.resumeUrl = resumeUrl;
   }
}