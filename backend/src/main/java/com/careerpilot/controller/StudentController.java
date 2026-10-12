package com.careerpilot.controller;

import com.careerpilot.dto.StudentRequest;
import com.careerpilot.dto.StudentResponse;
import com.careerpilot.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // GET MY PROFILE
    @GetMapping("/me")
    public ResponseEntity<StudentResponse> getMyProfile(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                studentService.getMyProfile(email)
        );
    }

    // CREATE
    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(
            @Valid @RequestBody StudentRequest request) {

        return ResponseEntity.ok(
                studentService.createStudent(request)
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {

        return ResponseEntity.ok(
                studentService.getAllStudents()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                studentService.getStudentById(id)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request) {

        return ResponseEntity.ok(
                studentService.updateStudent(id, request)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable Long id) {

        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }
}