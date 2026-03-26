package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Enrollment;
import edu.unimagdalena.lms.entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EnrollmentRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired private EnrollmentRepository enrollmentRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private CourseRepository courseRepository;

    private Student savedStudent;
    private Course savedCourse;

    @BeforeEach
    void setUp() {
        enrollmentRepository.deleteAll();
        courseRepository.deleteAll();
        studentRepository.deleteAll();

        Student student = new Student();
        student.setFullName("Grace Hopper");
        student.setEmail("grace@navy.mil");
        savedStudent = studentRepository.save(student);

        Course course = new Course();
        course.setTitle("Compilers 101");
        savedCourse = courseRepository.save(course);
    }

    @Test
    @DisplayName("Test saving an enrollment")
    void givenEnrollment_whenSave_thenReturnSavedEnrollment() {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(savedStudent);
        enrollment.setCourse(savedCourse);
        enrollment.setStatus("ACTIVE");
        enrollment.setEnrolledAt(Instant.now());

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        assertThat(savedEnrollment).isNotNull();
        assertThat(savedEnrollment.getId()).isGreaterThan(0);
        assertThat(savedEnrollment.getStudent().getFullName()).isEqualTo("Grace Hopper");
        assertThat(savedEnrollment.getCourse().getTitle()).isEqualTo("Compilers 101");
    }
}