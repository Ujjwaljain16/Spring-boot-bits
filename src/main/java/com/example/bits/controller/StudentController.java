package com.example.bits.controller;

import com.example.bits.entity.Course;
import com.example.bits.entity.Student;
import com.example.bits.exception.DuplicateEmailException;
import com.example.bits.repository.CourseRepository;
import com.example.bits.service.StudentService;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final CourseRepository courseRepository;

    public StudentController(StudentService studentService, CourseRepository courseRepository) {
        this.studentService = studentService;
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "list-students";
    }

    @GetMapping("/add")
    public String addStudentForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("courses", courseRepository.findAll());
        return "add-student";
    }

    @PostMapping
    public String createStudent(@Valid @ModelAttribute("student") Student student,
                                BindingResult bindingResult,
                                @RequestParam(value = "courseIds", required = false) List<Long> courseIds,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("courses", courseRepository.findAll());
            return "add-student";
        }

        student.setCourses(resolveCourses(courseIds));

        try {
            studentService.createStudent(student);
            return "redirect:/students";
        } catch (DuplicateEmailException ex) {
            bindingResult.rejectValue("email", "duplicate", ex.getMessage());
            model.addAttribute("courses", courseRepository.findAll());
            return "add-student";
        }
    }

    @GetMapping("/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        model.addAttribute("courses", courseRepository.findAll());
        return "edit-student";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id,
                                @Valid @ModelAttribute("student") Student student,
                                BindingResult bindingResult,
                                @RequestParam(value = "courseIds", required = false) List<Long> courseIds,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("courses", courseRepository.findAll());
            return "edit-student";
        }

        student.setCourses(resolveCourses(courseIds));

        try {
            studentService.updateStudent(id, student);
            return "redirect:/students";
        } catch (DuplicateEmailException ex) {
            bindingResult.rejectValue("email", "duplicate", ex.getMessage());
            model.addAttribute("courses", courseRepository.findAll());
            return "edit-student";
        }
    }

    private Set<Course> resolveCourses(List<Long> courseIds) {
        if (courseIds == null || courseIds.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(courseRepository.findAllById(courseIds));
    }
}