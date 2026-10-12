package com.careerpilot.service;



import com.careerpilot.dto.JobRequest;
import com.careerpilot.dto.JobResponse;
import com.careerpilot.entity.Job;
import com.careerpilot.repository.JobRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // ============================
    // CREATE JOB
    // ============================
    public JobResponse createJob(JobRequest request) {

        Job job = new Job();

        job.setTitle(request.getTitle());
        job.setCompany(request.getCompany());
        job.setLocation(request.getLocation());
        job.setJobType(request.getJobType());
        job.setDescription(request.getDescription());
        job.setSkills(request.getSkills());
        job.setSalary(request.getSalary());
        job.setApplicationUrl(request.getApplicationUrl());

        Job savedJob =
                jobRepository.save(job);

        return convertToResponse(savedJob);
    }

    // ============================
    // GET ALL JOBS
    // ============================
    public List<JobResponse> getAllJobs() {

        return jobRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // ============================
    // GET JOB BY ID
    // ============================
    public JobResponse getJobById(Long id) {

        Job job =
                jobRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job not found with id: " + id
                                )
                        );

        return convertToResponse(job);
    }

    // ============================
    // UPDATE JOB
    // ============================
    public JobResponse updateJob(
            Long id,
            JobRequest request) {

        Job job =
                jobRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job not found with id: " + id
                                )
                        );

        job.setTitle(request.getTitle());
        job.setCompany(request.getCompany());
        job.setLocation(request.getLocation());
        job.setJobType(request.getJobType());
        job.setDescription(request.getDescription());
        job.setSkills(request.getSkills());
        job.setSalary(request.getSalary());
        job.setApplicationUrl(request.getApplicationUrl());

        Job updatedJob =
                jobRepository.save(job);

        return convertToResponse(updatedJob);
    }

    // ============================
    // DELETE JOB
    // ============================
    public void deleteJob(Long id) {

        if (!jobRepository.existsById(id)) {

            throw new RuntimeException(
                    "Job not found with id: " + id
            );
        }

        jobRepository.deleteById(id);
    }

    // ============================
    // ENTITY → RESPONSE
    // ============================
    private JobResponse convertToResponse(Job job) {

        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getCompany(),
                job.getLocation(),
                job.getJobType(),
                job.getDescription(),
                job.getSkills(),
                job.getSalary(),
                job.getApplicationUrl()
        );
    }
}
