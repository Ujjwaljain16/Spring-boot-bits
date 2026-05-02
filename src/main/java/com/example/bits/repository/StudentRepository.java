package com.example.bits.repository;

import com.example.bits.entity.Student;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    @Query("select distinct s from Student s join fetch s.courses")
    List<Student> findAllWithCourses();
}