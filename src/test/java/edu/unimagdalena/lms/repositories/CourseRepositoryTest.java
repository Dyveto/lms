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

        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop"); // Crea todas las entidades en la base de datos
    }

    @Autowired
    private CourseRepository courseRepository;

    private Course courseToTest;

    // Se ejecuta antes de cada @Test para tener datos limpios
    @BeforeEach
    void setUp() {
        courseRepository.deleteAll(); // se limpia la tabla

        courseToTest = Course.builder()
                .title("Spring Boot")
                .status("DRAFT")
                .active(true)
                .build();
    }

    @Test
    @DisplayName("Test para crear un curso")
    void testCreateCourse() {
        // Arrange (preparar): se hace en el setUp()

        // Act (actuar): Guarda el curso
        Course savedCourse = courseRepository.save(courseToTest);

        // Assert (afirmar/verificar): Comprobamos que se guardo correctamente
        assertThat(savedCourse).isNotNull();
        assertThat(savedCourse.getId()).isGreaterThan(0); // si la base de datos le asigna ID, es que si se guardo
        assertThat(savedCourse.getTitle()).isEqualTo("Spring Boot");
    }

    @Test
    @DisplayName("Test para leer un curso por ID")
    void testReadCourse() {
        // Arrange: guardo un curso para poder buscarlo
        Course savedCourse = courseRepository.save(courseToTest);

        // Act: se busca el curso por su ID
        Optional<Course> foundCourse = courseRepository.findById(savedCourse.getId());

        // Assert: verifica si lo encontro y si es el correcto
        assertThat(foundCourse).isPresent();
        assertThat(foundCourse.get().getStatus()).isEqualTo("DRAFT");
    }

    @Test
    @DisplayName("Test para actualizar un curso")
    void testUpdateCourse() {
        // Arrange: guardo el curso inicial
        Course savedCourse = courseRepository.save(courseToTest);

        // Act: Modifico los datos y los vuelvo a guardar
        savedCourse.setTitle("Spring Boot Actualizado");
        savedCourse.setActive(false);
        Course updatedCourse = courseRepository.save(savedCourse);

        // Assert: verifico que los cambios se actualizaron
        assertThat(updatedCourse.getTitle()).isEqualTo("Spring Boot Actualizado");
        assertThat(updatedCourse.getActive()).isFalse();
    }

    @Test
    @DisplayName("Test para eliminar un curso")
    void testDeleteCourse() {
        // Arrange: guarda el curso inicial
        Course savedCourse = courseRepository.save(courseToTest);

        // Act: elimina el curso usando su ID
        courseRepository.deleteById(savedCourse.getId());

        // busca el curso de nuevo
        Optional<Course> deletedCourse = courseRepository.findById(savedCourse.getId());

        // Assert: verifica que el curso ya no exista
        assertThat(deletedCourse).isEmpty();
    }
}
