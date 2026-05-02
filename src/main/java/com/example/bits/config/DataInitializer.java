package com.example.bits.config;

import com.example.bits.entity.Course;
import com.example.bits.entity.Student;
import com.example.bits.repository.CourseRepository;
import com.example.bits.repository.StudentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedDatabase(DataSeedService seedService) {
        return args -> seedService.seedDatabase();
    }

    @Service
    public static class DataSeedService {
        private final StudentRepository studentRepository;
        private final CourseRepository courseRepository;

        public DataSeedService(StudentRepository studentRepository, CourseRepository courseRepository) {
            this.studentRepository = studentRepository;
            this.courseRepository = courseRepository;
        }

        @Transactional
        public void seedDatabase() {
            if (courseRepository.count() == 0) {
                List<Course> courses = new ArrayList<>();
                for (int i = 1; i <= 10; i++) {
                    courses.add(new Course("Course " + i, "Description for Course " + i));
                }
                courseRepository.saveAll(courses);
            }

            if (studentRepository.count() == 0) {
                List<Course> allCourses = courseRepository.findAll();
                Random random = new Random();
                for (int i = 1; i <= 10; i++) {
                    Student student = new Student("Student " + i, "student" + i + "@example.com");
                    int courseCount = 1 + random.nextInt(4);
                    for (int j = 0; j < courseCount; j++) {
                        student.addCourse(allCourses.get(random.nextInt(allCourses.size())));
                    }
                    studentRepository.save(student);
                }
            }
        }
    }
}