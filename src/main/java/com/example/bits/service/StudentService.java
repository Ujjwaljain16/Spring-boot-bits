package com.example.bits.service;

import com.example.bits.entity.Student;
import java.util.List;

public interface StudentService {

    Student createStudent(Student student);

    Student updateStudent(Long id, Student student);

    Student getStudentById(Long id);

    List<Student> getAllStudents();
}