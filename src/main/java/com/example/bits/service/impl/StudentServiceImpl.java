package com.example.bits.service.impl;

import com.example.bits.entity.Course;
import com.example.bits.entity.Student;
import com.example.bits.exception.DuplicateEmailException;
import com.example.bits.repository.CourseRepository;
import com.example.bits.repository.StudentRepository;
import com.example.bits.service.StudentService;
import java.util.HashSet;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Student createStudent(Student student) {
        studentRepository.findByEmail(student.getEmail()).ifPresent(existing -> {
            throw new DuplicateEmailException("Email already exists: " + student.getEmail());
        });
        try {
            return studentRepository.save(student);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateEmailException("Email already exists: " + student.getEmail());
        }
    }

    @Override
    public Student updateStudent(Long id, Student updatedStudent) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + id));

        studentRepository.findByEmail(updatedStudent.getEmail()).ifPresent(other -> {
            if (!other.getId().equals(id)) {
                throw new DuplicateEmailException("Email already exists: " + updatedStudent.getEmail());
            }
        });

        existing.setName(updatedStudent.getName());
        existing.setEmail(updatedStudent.getEmail());
        existing.setCourses(new HashSet<>(updatedStudent.getCourses()));
        return studentRepository.save(existing);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + id));
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAllWithCourses();
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}