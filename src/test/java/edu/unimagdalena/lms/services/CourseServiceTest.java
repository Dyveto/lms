package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.CourseRequestDto;
import edu.unimagdalena.lms.dto.response.CourseResponseDto;
import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Instructor;
import edu.unimagdalena.lms.repositories.CourseRepository;
import edu.unimagdalena.lms.repositories.InstructorRepository;
import edu.unimagdalena.lms.services.mapper.CourseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock private CourseRepository courseRepository;
    @Mock private InstructorRepository instructorRepository;
    @Mock private CourseMapper courseMapper;

    @InjectMocks private CourseServiceImpl courseService;

    private Course course;
    private CourseRequestDto requestDto;
    private CourseResponseDto responseDto;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setId(1L);
        course.setTitle("Ingeniería de Software II");
        course.setStatus("ACTIVO");

        requestDto = new CourseRequestDto("Ingeniería de Software II", "ACTIVO", true, 1L);
        responseDto = new CourseResponseDto(1L, "Ingeniería de Software II", "ACTIVO", true, 1L);
    }

    @Test
    @DisplayName("Prueba para crear un curso")
    void givenCourseRequest_whenCreate_thenReturnCourseResponse() {
        Instructor dummyInstructor = new Instructor();
        dummyInstructor.setId(1L);

        given(instructorRepository.findById(anyLong())).willReturn(Optional.of(dummyInstructor));
        given(courseMapper.toEntity(any(CourseRequestDto.class))).willReturn(course);
        given(courseRepository.save(any(Course.class))).willReturn(course);
        given(courseMapper.toDto(any(Course.class))).willReturn(responseDto);

        CourseResponseDto savedCourse = courseService.create(requestDto);

        assertThat(savedCourse).isNotNull();
        assertThat(savedCourse.getTitle()).isEqualTo("Ingeniería de Software II");
        verify(courseRepository).save(any(Course.class));
    }
}