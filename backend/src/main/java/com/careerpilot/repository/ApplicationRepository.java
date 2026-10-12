package com.careerpilot.repository;

import com.careerpilot.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository
        extends JpaRepository<Application, Long> {

    List<Application> findByStudentEmail(String studentEmail);

    boolean existsByJobIdAndStudentEmail(
            Long jobId,
            String studentEmail
    );
}