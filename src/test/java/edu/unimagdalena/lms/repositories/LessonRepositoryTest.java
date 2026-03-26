package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Instructor;
import edu.unimagdalena.lms.entities.Lesson;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LessonRepositoryTest extends BaseRepositoryTest {

    @Autowired private LessonRepository lessonRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private InstructorRepository instructorRepository;

    private Course savedCourse;

    @BeforeEach
    void setUp() {
        lessonRepository.deleteAll();
        courseRepository.deleteAll();
        instructorRepository.deleteAll();

        Instructor instructor = new Instructor();
        instructor.setFullName("Linus Torvalds");
        instructor.setEmail("linus@linux.org");
        Instructor savedInstructor = instructorRepository.save(instructor);

        Course course = new Course();
        course.setTitle("Advanced Operating Systems");
        course.setInstructor(savedInstructor);
        savedCourse = courseRepository.save(course);
    }

    @Test
    @DisplayName("Test saving a lesson linked to a course")
    void givenLesson_whenSave_thenReturnSavedLesson() {
        Lesson lesson = new Lesson();
        lesson.setTitle("Kernel Architecture");
        lesson.setOrderIndex(1);
        lesson.setCourse(savedCourse);

        Lesson savedLesson = lessonRepository.save(lesson);

        assertThat(savedLesson).isNotNull();
        assertThat(savedLesson.getId()).isGreaterThan(0);
        assertThat(savedLesson.getCourse().getTitle()).isEqualTo("Advanced Operating Systems");
    }
}