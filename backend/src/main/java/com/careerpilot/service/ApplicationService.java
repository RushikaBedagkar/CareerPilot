package com.careerpilot.service;

import com.careerpilot.dto.ApplicationRequest;
import com.careerpilot.dto.ApplicationResponse;
import com.careerpilot.entity.Application;
import com.careerpilot.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(
            ApplicationRepository applicationRepository) {

        this.applicationRepository = applicationRepository;
    }

    public ApplicationResponse apply(
            ApplicationRequest request,
            String studentEmail) {

        boolean alreadyApplied =
                applicationRepository
                        .existsByJobIdAndStudentEmail(
                                request.getJobId(),
                                studentEmail
                        );

        if (alreadyApplied) {
            throw new RuntimeException(
                    "You have already applied for this job."
            );
        }

        Application application = new Application();

        application.setJobId(request.getJobId());
        application.setStudentEmail(studentEmail);
        application.setStatus("PENDING");
        application.setAppliedDate(
                LocalDate.now().toString()
        );

        Application saved =
                applicationRepository.save(application);

        return convertToResponse(saved);
    }

    public List<ApplicationResponse> getMyApplications(
            String studentEmail) {

        return applicationRepository
                .findByStudentEmail(studentEmail)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private ApplicationResponse convertToResponse(
            Application application) {

        return new ApplicationResponse(
                application.getId(),
                application.getJobId(),
                application.getStudentEmail(),
                application.getStatus(),
                application.getAppliedDate()
        );
    }
    public List<ApplicationResponse> getAllApplications() {

        return applicationRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public ApplicationResponse updateStatus(
            Long id,
            String status) {

        Application application =
                applicationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found"
                                ));

        application.setStatus(status);

        Application updated =
                applicationRepository.save(application);

        return convertToResponse(updated);
    }
}
