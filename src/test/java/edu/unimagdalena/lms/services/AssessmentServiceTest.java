package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.AssessmentRequestDto;
import edu.unimagdalena.lms.dto.response.AssessmentResponseDto;
import edu.unimagdalena.lms.entities.Assessment;
import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Student;
import edu.unimagdalena.lms.repositories.AssessmentRepository;
import edu.unimagdalena.lms.repositories.CourseRepository;
import edu.unimagdalena.lms.repositories.StudentRepository;
import edu.unimagdalena.lms.services.mapper.AssessmentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AssessmentServiceTest {

    @Mock private AssessmentRepository assessmentRepository;
    @Mock private StudentRepository studentRepository;
    @Mock private CourseRepository courseRepository;
    @Mock private AssessmentMapper assessmentMapper;

    @InjectMocks private AssessmentServiceImpl assessmentService;

    private Assessment assessment;
    private AssessmentRequestDto requestDto;
    private AssessmentResponseDto responseDto;

    @BeforeEach
    void setUp() {
        assessment = new Assessment();
        assessment.setId(1L);
        assessment.setScore(95);
        assessment.setType("EXAMEN_FINAL");
        assessment.setTakenAt(Instant.now());

        requestDto = new AssessmentRequestDto(1L, 1L, "EXAMEN_FINAL", 95, assessment.getTakenAt());
        
        responseDto = new AssessmentResponseDto(1L, 1L, 1L, "EXAMEN_FINAL", 95, assessment.getTakenAt());
    }

    @Test
    @DisplayName("Prueba para crear una evaluación")
    void givenAssessmentRequest_whenCreate_thenReturnAssessmentResponse() {
        Student dummyStudent = new Student();
        dummyStudent.setId(1L);
        Course dummyCourse = new Course();
        dummyCourse.setId(1L);

        given(studentRepository.findById(anyLong())).willReturn(Optional.of(dummyStudent));
        given(courseRepository.findById(anyLong())).willReturn(Optional.of(dummyCourse));
        
        given(assessmentMapper.toEntity(any(AssessmentRequestDto.class))).willReturn(assessment);
        given(assessmentRepository.save(any(Assessment.class))).willReturn(assessment);
        given(assessmentMapper.toDto(any(Assessment.class))).willReturn(responseDto);

        AssessmentResponseDto savedAssessment = assessmentService.create(requestDto);

        assertThat(savedAssessment).isNotNull();
        assertThat(savedAssessment.getScore()).isEqualTo(95);
        assertThat(savedAssessment.getType()).isEqualTo("EXAMEN_FINAL");
        verify(assessmentRepository).save(any(Assessment.class));
    }
}