package com.example.bits.repository;

import com.example.bits.entity.Course;
import com.example.bits.entity.Student;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void findAllWithCoursesReturnsJoinedStudents() {
        Course course = courseRepository.save(new Course("Spring", "Spring Boot"));
        Student student = new Student("Jane", "jane@example.com");
        student.getCourses().add(course);
        studentRepository.save(student);

        List<Student> students = studentRepository.findAllWithCourses();

        Assertions.assertFalse(students.isEmpty());
        Assertions.assertEquals(1, students.get(0).getCourses().size());
    }
}