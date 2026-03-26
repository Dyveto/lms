package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Instructor;
import edu.unimagdalena.lms.entities.InstructorProfile;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InstructorProfileRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired private InstructorProfileRepository instructorProfileRepository;
    @Autowired private InstructorRepository instructorRepository;

    private Instructor savedInstructor;

    @BeforeEach
    void setUp() {
        instructorProfileRepository.deleteAll();
        instructorRepository.deleteAll();

        Instructor instructor = new Instructor();
        instructor.setFullName("Marie Curie");
        instructor.setEmail("marie.curie@science.org");
        savedInstructor = instructorRepository.save(instructor);
    }

    @Test
    @DisplayName("Test saving an instructor profile")
    void givenInstructorProfile_whenSave_thenReturnSavedProfile() {
        InstructorProfile profile = new InstructorProfile();
        profile.setBio("Pioneer in research on radioactivity.");
        profile.setPhone("+1234567890");
        profile.setInstructor(savedInstructor);

        InstructorProfile savedProfile = instructorProfileRepository.save(profile);

        assertThat(savedProfile).isNotNull();
        assertThat(savedProfile.getId()).isGreaterThan(0);
        assertThat(savedProfile.getBio()).isEqualTo("Pioneer in research on radioactivity.");
        assertThat(savedProfile.getInstructor().getFullName()).isEqualTo("Marie Curie");
    }

    @Test
    @DisplayName("Test finding instructor profile by ID")
    void givenProfileId_whenFindById_thenReturnProfile() {
        InstructorProfile profile = new InstructorProfile();
        profile.setBio("Expert in Physics.");
        profile.setInstructor(savedInstructor);
        InstructorProfile savedProfile = instructorProfileRepository.save(profile);

        Optional<InstructorProfile> foundProfile = instructorProfileRepository.findById(savedProfile.getId());

        assertThat(foundProfile).isPresent();
        assertThat(foundProfile.get().getBio()).isEqualTo("Expert in Physics.");
    }
}