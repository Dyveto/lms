package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Instructor;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InstructorRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    private InstructorRepository instructorRepository;

    private Instructor instructor;

    @BeforeEach
    void setUp() {
        instructorRepository.deleteAll();
        
        instructor = new Instructor();
        instructor.setFullName("Alan Turing");
        instructor.setEmail("alan.turing@example.com");
    }

    @Test
    @DisplayName("Test saving an instructor")
    void givenInstructor_whenSave_thenReturnSavedInstructor() {
        Instructor savedInstructor = instructorRepository.save(instructor);

        assertThat(savedInstructor).isNotNull();
        assertThat(savedInstructor.getId()).isGreaterThan(0);
        assertThat(savedInstructor.getEmail()).isEqualTo("alan.turing@example.com");
    }

    @Test
    @DisplayName("Test finding all instructors")
    void givenInstructors_whenFindAll_thenReturnList() {
        Instructor instructor2 = new Instructor();
        instructor2.setFullName("Ada Lovelace");
        instructor2.setEmail("ada@example.com");

        instructorRepository.save(instructor);
        instructorRepository.save(instructor2);

        List<Instructor> instructors = instructorRepository.findAll();

        assertThat(instructors).hasSize(2);
    }
}