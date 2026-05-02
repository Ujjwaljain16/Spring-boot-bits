# Student Course Management System

Spring Boot + JSP application for managing students and courses with a many-to-many relationship.

## Features

- Add, list, and edit students
- Assign multiple courses to each student
- JPQL join query to fetch students with courses
- CommandLineRunner seeds 10 students and 10 courses
- Unit tests for service and repository

## Project Structure

```text
src/main/java/com/example/bits
├── BitsApplication.java
├── config/DataInitializer.java
├── controller/StudentController.java
├── entity/Course.java
├── entity/Student.java
├── exception/DuplicateEmailException.java
├── repository/CourseRepository.java
├── repository/StudentRepository.java
└── service
    ├── StudentService.java
    └── impl/StudentServiceImpl.java

src/main/resources
├── application.properties
└── static/css/style.css

src/main/webapp/WEB-INF/jsp
├── add-student.jsp
├── edit-student.jsp
└── list-students.jsp
```

## Run

```powershell
cd C:\Users\ujjwa\OneDrive\Desktop\Hack\bits
mvn test
mvn spring-boot:run
```

## MySQL

Uncomment the MySQL properties in `src/main/resources/application.properties` and point them to your database.# Spring-boot-bits
