package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.EnrollmentRequestDto;
import edu.unimagdalena.lms.dto.response.EnrollmentResponseDto;
import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Enrollment;
import edu.unimagdalena.lms.entities.Student;
import edu.unimagdalena.lms.repositories.CourseRepository;
import edu.unimagdalena.lms.repositories.EnrollmentRepository;
import edu.unimagdalena.lms.repositories.StudentRepository;
import edu.unimagdalena.lms.services.mapper.EnrollmentMapper;
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
class EnrollmentServiceTest {

    @Mock private EnrollmentRepository enrollmentRepository;
    @Mock private StudentRepository studentRepository;
    @Mock private CourseRepository courseRepository;
    @Mock private EnrollmentMapper enrollmentMapper;

    @InjectMocks private EnrollmentServiceImpl enrollmentService;

    private Enrollment enrollment;
    private EnrollmentRequestDto requestDto;
    private EnrollmentResponseDto responseDto;

    @BeforeEach
    void setUp() {
        enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setStatus("MATRICULADO");
        Instant enrollmentTime = Instant.now();

        requestDto = new EnrollmentRequestDto(1L, 1L, "MATRICULADO", enrollmentTime);
        responseDto = new EnrollmentResponseDto(1L, 1L, 1L, "MATRICULADO", enrollmentTime);
    }

    @Test
    @DisplayName("Prueba para crear una matrícula")
    void givenEnrollmentRequest_whenCreate_thenReturnEnrollmentResponse() {
        Student dummyStudent = new Student();
        dummyStudent.setId(1L);
        Course dummyCourse = new Course();
        dummyCourse.setId(1L);

        given(studentRepository.findById(anyLong())).willReturn(Optional.of(dummyStudent));
        given(courseRepository.findById(anyLong())).willReturn(Optional.of(dummyCourse));
        given(enrollmentMapper.toEntity(any(EnrollmentRequestDto.class))).willReturn(enrollment);
        given(enrollmentRepository.save(any(Enrollment.class))).willReturn(enrollment);
        given(enrollmentMapper.toDto(any(Enrollment.class))).willReturn(responseDto);

        EnrollmentResponseDto savedEnrollment = enrollmentService.create(requestDto);

        assertThat(savedEnrollment).isNotNull();
        assertThat(savedEnrollment.getStatus()).isEqualTo("MATRICULADO");
        verify(enrollmentRepository).save(any(Enrollment.class));
    }
}