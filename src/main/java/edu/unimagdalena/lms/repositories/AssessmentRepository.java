package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    // Evaluaciones de un estudiante en un rango
    List<Assessment> findByStudentIdAndTakenAtBetween(Long StudentId, Instant start, Instant end);
}