
        package com.careerpilot.controller;

import com.careerpilot.dto.JobRequest;
import com.careerpilot.dto.JobResponse;
import com.careerpilot.service.JobService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174"
})
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // ============================
    // CREATE JOB
    // POST /api/jobs
    // ============================
    @PostMapping
    public ResponseEntity<JobResponse> createJob(
            @RequestBody JobRequest request) {

        JobResponse response =
                jobService.createJob(request);

        return ResponseEntity.ok(response);
    }

    // ============================
    // GET ALL JOBS
    // GET /api/jobs
    // ============================
    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {

        List<JobResponse> jobs =
                jobService.getAllJobs();

        return ResponseEntity.ok(jobs);
    }

    // ============================
    // GET JOB BY ID
    // GET /api/jobs/{id}
    // ============================
    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(
            @PathVariable Long id) {

        JobResponse job =
                jobService.getJobById(id);

        return ResponseEntity.ok(job);
    }

    // ============================
    // UPDATE JOB
    // PUT /api/jobs/{id}
    // ============================
    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(
            @PathVariable Long id,
            @RequestBody JobRequest request) {

        JobResponse existingJob =
                jobService.getJobById(id);

        /*
         * We use the existing JobResponse values and
         * create a new request with updated values.
         */

        JobRequest updatedRequest =
                new JobRequest();

        updatedRequest.setTitle(
                request.getTitle() != null
                        ? request.getTitle()
                        : existingJob.getTitle()
        );

        updatedRequest.setCompany(
                request.getCompany() != null
                        ? request.getCompany()
                        : existingJob.getCompany()
        );

        updatedRequest.setLocation(
                request.getLocation() != null
                        ? request.getLocation()
                        : existingJob.getLocation()
        );

        updatedRequest.setJobType(
                request.getJobType() != null
                        ? request.getJobType()
                        : existingJob.getJobType()
        );

        updatedRequest.setDescription(
                request.getDescription() != null
                        ? request.getDescription()
                        : existingJob.getDescription()
        );

        updatedRequest.setSkills(
                request.getSkills() != null
                        ? request.getSkills()
                        : existingJob.getSkills()
        );

        updatedRequest.setSalary(
                request.getSalary() != null
                        ? request.getSalary()
                        : existingJob.getSalary()
        );

        updatedRequest.setApplicationUrl(
                request.getApplicationUrl() != null
                        ? request.getApplicationUrl()
                        : existingJob.getApplicationUrl()
        );

        /*
         * IMPORTANT:
         * This method requires an updateJob() method
         * in JobService.
         */

        JobResponse response =
                jobService.updateJob(id, updatedRequest);

        return ResponseEntity.ok(response);
    }

    // ============================
    // DELETE JOB
    // DELETE /api/jobs/{id}
    // ============================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(
            @PathVariable Long id) {

        jobService.deleteJob(id);

        return ResponseEntity.ok(
                "Job deleted successfully"
        );
    }
}
