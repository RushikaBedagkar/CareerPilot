package com.careerpilot.service;

import com.careerpilot.dto.StudentRequest;
import com.careerpilot.dto.StudentResponse;
import com.careerpilot.entity.Student;
import com.careerpilot.exception.StudentNotFoundException;
import com.careerpilot.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // CREATE
    public StudentResponse createStudent(StudentRequest request) {

        Student student = new Student();

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setCollege(request.getCollege());
        student.setDegree(request.getDegree());
        student.setGraduationYear(request.getGraduationYear());
        student.setCgpa(request.getCgpa());
        student.setGithubUrl(request.getGithubUrl());
        student.setLinkedinUrl(request.getLinkedinUrl());
        student.setResumeUrl(request.getResumeUrl());

        Student savedStudent = studentRepository.save(student);

        return convertToResponse(savedStudent);
    }

    // GET MY PROFILE
    public StudentResponse getMyProfile(String email) {

        Student student = studentRepository
                .findByEmail(email)
                .orElse(null);

        if (student == null) {

            student = new Student();

            student.setName("New Student");
            student.setEmail(email);

            student.setPhone(null);
            student.setCollege(null);
            student.setDegree(null);
            student.setGraduationYear(null);
            student.setCgpa(null);
            student.setGithubUrl(null);
            student.setLinkedinUrl(null);
            student.setResumeUrl(null);

            student = studentRepository.save(student);
        }

        return convertToResponse(student);

    }

    // GET ALL
    public List<StudentResponse> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY ID
    public StudentResponse getStudentById(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with id: " + id
                        ));

        return convertToResponse(student);
    }

    // UPDATE
    public StudentResponse updateStudent(
            Long id,
            StudentRequest request) {

        Student existingStudent =
                studentRepository.findById(id)
                        .orElseThrow(() ->
                                new StudentNotFoundException(
                                        "Student not found with id: " + id
                                ));

        existingStudent.setName(request.getName());
        existingStudent.setEmail(request.getEmail());
        existingStudent.setPhone(request.getPhone());
        existingStudent.setCollege(request.getCollege());
        existingStudent.setDegree(request.getDegree());
        existingStudent.setGraduationYear(request.getGraduationYear());
        existingStudent.setCgpa(request.getCgpa());
        existingStudent.setGithubUrl(request.getGithubUrl());
        existingStudent.setLinkedinUrl(request.getLinkedinUrl());
        existingStudent.setResumeUrl(request.getResumeUrl());

        Student updatedStudent =
                studentRepository.save(existingStudent);

        return convertToResponse(updatedStudent);
    }
    public StudentResponse getStudentByEmail(String email) {

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student profile not found for email: " + email
                        )
                );

        return convertToResponse(student);
    }

    // DELETE
    public void deleteStudent(Long id) {

        if (!studentRepository.existsById(id)) {

            throw new StudentNotFoundException(
                    "Student not found with id: " + id
            );
        }

        studentRepository.deleteById(id);
    }


    // ENTITY → RESPONSE
    private StudentResponse convertToResponse(Student student) {

        return new StudentResponse(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getPhone(),
                student.getCollege(),
                student.getDegree(),
                student.getGraduationYear(),
                student.getCgpa(),
                student.getGithubUrl(),
                student.getLinkedinUrl(),
                student.getResumeUrl()
        );
    }
}