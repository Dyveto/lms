package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CourseRepositoryTest {

    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:15-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired
    private CourseRepository courseRepository;

    private Course courseToTest;

    @BeforeEach
    void setUp() {
        courseRepository.deleteAll();

        courseToTest = Course.builder()
                .title("Spring Boot")
                .status("DRAFT")
                .active(true)
                .build();
    }

    @Test
    @DisplayName("Test para crear un curso")
    void testCreateCourse() {

        Course savedCourse = courseRepository.save(courseToTest);

        assertThat(savedCourse).isNotNull();
        assertThat(savedCourse.getId()).isGreaterThan(0);
        assertThat(savedCourse.getTitle()).isEqualTo("Spring Boot");
    }

    @Test
    @DisplayName("Test para leer un curso por ID")
    void testReadCourse() {
        Course savedCourse = courseRepository.save(courseToTest);

        Optional<Course> foundCourse = courseRepository.findById(savedCourse.getId());

        assertThat(foundCourse).isPresent();
        assertThat(foundCourse.get().getStatus()).isEqualTo("DRAFT");
    }

    @Test
    @DisplayName("Test para actualizar un curso")
    void testUpdateCourse() {
        Course savedCourse = courseRepository.save(courseToTest);

        savedCourse.setTitle("Spring Boot Actualizado");
        savedCourse.setActive(false);
        Course updatedCourse = courseRepository.save(savedCourse);

        assertThat(updatedCourse.getTitle()).isEqualTo("Spring Boot Actualizado");
        assertThat(updatedCourse.getActive()).isFalse();
    }

    @Test
    @DisplayName("Test para eliminar un curso")
    void testDeleteCourse() {
        Course savedCourse = courseRepository.save(courseToTest);

        courseRepository.deleteById(savedCourse.getId());

        Optional<Course> deletedCourse = courseRepository.findById(savedCourse.getId());

        assertThat(deletedCourse).isEmpty();
    }
}
