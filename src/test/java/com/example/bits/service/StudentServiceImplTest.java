package com.example.bits.service;

import com.example.bits.entity.Course;
import com.example.bits.entity.Student;
import com.example.bits.exception.DuplicateEmailException;
import com.example.bits.repository.CourseRepository;
import com.example.bits.repository.StudentRepository;
import com.example.bits.service.impl.StudentServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student("Alice", "alice@example.com");
    }

    @Test
    void createStudentSavesStudentWhenEmailIsUnique() {
        Mockito.when(studentRepository.findByEmail("alice@example.com")).thenReturn(Optional.empty());
        Mockito.when(studentRepository.save(student)).thenReturn(student);

        Student saved = studentService.createStudent(student);

        Assertions.assertEquals("Alice", saved.getName());
        Mockito.verify(studentRepository).save(student);
    }

    @Test
    void createStudentThrowsWhenDuplicateEmailExists() {
        Mockito.when(studentRepository.findByEmail("alice@example.com"))
                .thenReturn(Optional.of(new Student("Existing", "alice@example.com")));

        Assertions.assertThrows(DuplicateEmailException.class, () -> studentService.createStudent(student));
    }

    @Test
    void updateStudentCopiesBasicFieldsAndCourses() {
        Student existing = new Student("Old", "old@example.com");
        existing.setId(1L);
        Course course = new Course("Java", "Java course");
        course.setId(10L);

        Student updated = new Student("New", "new@example.com");
        updated.setCourses(List.of(course).stream().collect(java.util.stream.Collectors.toSet()));

        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(studentRepository.findByEmail("new@example.com")).thenReturn(Optional.empty());
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Student result = studentService.updateStudent(1L, updated);

        Assertions.assertEquals("New", result.getName());
        Assertions.assertEquals("new@example.com", result.getEmail());
        Assertions.assertEquals(1, result.getCourses().size());
    }
}