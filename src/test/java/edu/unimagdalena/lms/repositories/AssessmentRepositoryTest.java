package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Assessment;
import edu.unimagdalena.lms.entities.Course;
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

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AssessmentRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired private AssessmentRepository assessmentRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private InstructorRepository instructorRepository;

    private Student savedStudent;
    private Course savedCourse;

    @BeforeEach
    void setUp() {
        assessmentRepository.deleteAll();
        courseRepository.deleteAll();
        studentRepository.deleteAll();
        instructorRepository.deleteAll();

        Student student = new Student();
        student.setFullName("Nikola Tesla");
        student.setEmail("tesla@energy.com");
        savedStudent = studentRepository.save(student);

        Instructor instructor = new Instructor();
        instructor.setFullName("Thomas Edison");
        instructor.setEmail("edison@raton.com");
        Instructor savedInstructor = instructorRepository.save(instructor);

        Course course = new Course();
        course.setTitle("Electrical Engineering 101");
        course.setInstructor(savedInstructor);
        savedCourse = courseRepository.save(course);
    }

    @Test
    @DisplayName("Test saving an assessment")
    void givenAssessment_whenSave_thenReturnSavedAssessment() {
        Assessment assessment = new Assessment();
        assessment.setScore(95);
        assessment.setType("FINAL_EXAM");
        assessment.setTakenAt(Instant.now());
        assessment.setStudent(savedStudent);
        assessment.setCourse(savedCourse);

        Assessment savedAssessment = assessmentRepository.save(assessment);

        assertThat(savedAssessment).isNotNull();
        assertThat(savedAssessment.getId()).isGreaterThan(0);
        assertThat(savedAssessment.getScore()).isEqualTo(95);
        assertThat(savedAssessment.getStudent().getFullName()).isEqualTo("Nikola Tesla");
        assertThat(savedAssessment.getCourse().getTitle()).isEqualTo("Electrical Engineering 101");
    }

    @Test
    @DisplayName("Test finding all assessments")
    void givenAssessments_whenFindAll_thenReturnList() {
        Assessment assessment1 = new Assessment();
        assessment1.setScore(80);
        assessment1.setStudent(savedStudent);
        assessment1.setCourse(savedCourse);

        Assessment assessment2 = new Assessment();
        assessment2.setScore(90);
        assessment2.setStudent(savedStudent);
        assessment2.setCourse(savedCourse);

        assessmentRepository.save(assessment1);
        assessmentRepository.save(assessment2);

        List<Assessment> assessments = assessmentRepository.findAll();

        assertThat(assessments).hasSize(2);
    }
}