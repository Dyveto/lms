package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Enrollment;
import edu.unimagdalena.lms.entities.Instructor;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudentRepositoryTest extends BaseRepositoryTest {

    @Autowired private StudentRepository studentRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private InstructorRepository instructorRepository;
    @Autowired private EnrollmentRepository enrollmentRepository;

    private Student student;

    @BeforeEach
    void setUp() {
        enrollmentRepository.deleteAll();
        courseRepository.deleteAll();
        studentRepository.deleteAll();
        instructorRepository.deleteAll();
        
        student = new Student();
        student.setFullName("John Doe");
        student.setEmail("john.doe@example.com");
    }

    @Test
    @DisplayName("Test saving a student")
    void givenStudentObject_whenSave_thenReturnSavedStudent() {
        Student savedStudent = studentRepository.save(student);

        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId()).isGreaterThan(0);
        assertThat(savedStudent.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    @DisplayName("Test getting all students")
    void givenStudentsList_whenFindAll_thenStudentsList() {
        Student student2 = new Student();
        student2.setFullName("Jane Doe");
        student2.setEmail("jane.doe@example.com");
        
        studentRepository.save(student);
        studentRepository.save(student2);

        List<Student> studentList = studentRepository.findAll();

        assertThat(studentList).isNotNull();
        assertThat(studentList).hasSize(2);
    }

    @Test
    @DisplayName("Test finding students by course ID")
    void givenCourseId_whenFindStudentByCourseId_thenReturnStudentsList() {
        Student savedStudent = studentRepository.save(student);

        Instructor instructor = new Instructor();
        instructor.setFullName("Alan Turing");
        instructor.setEmail("alan@example.com");
        Instructor savedInstructor = instructorRepository.save(instructor);

        Course course = new Course();
        course.setTitle("Computer Science 101");
        course.setInstructor(savedInstructor); // Link to instructor
        Course savedCourse = courseRepository.save(course);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(savedStudent);
        enrollment.setCourse(savedCourse);
        enrollment.setStatus("ACTIVE");
        enrollment.setEnrolledAt(Instant.now());
        enrollmentRepository.save(enrollment);

        List<Student> studentsInCourse = studentRepository.findStudentByCourseId(savedCourse.getId());

        assertThat(studentsInCourse).isNotNull();
        assertThat(studentsInCourse).hasSize(1);
        assertThat(studentsInCourse.get(0).getFullName()).isEqualTo("John Doe");
    }
}