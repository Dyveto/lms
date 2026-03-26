package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.LessonRequestDto;
import edu.unimagdalena.lms.dto.response.LessonResponseDto;
import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Lesson;
import edu.unimagdalena.lms.repositories.CourseRepository;
import edu.unimagdalena.lms.repositories.LessonRepository;
import edu.unimagdalena.lms.services.mapper.LessonMapper;
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
class LessonServiceTest {

    @Mock private LessonRepository lessonRepository;
    @Mock private CourseRepository courseRepository;
    @Mock private LessonMapper lessonMapper;

    @InjectMocks private LessonServiceImpl lessonService;

    private Lesson lesson;
    private LessonRequestDto requestDto;
    private LessonResponseDto responseDto;

    @BeforeEach
    void setUp() {
        lesson = new Lesson();
        lesson.setId(1L);
        lesson.setTitle("Introducción a Spring Boot");
        lesson.setOrderIndex(1);

        requestDto = new LessonRequestDto(1L, "Introducción a Spring Boot", 1);
        responseDto = new LessonResponseDto(1L, 1L, "Introducción a Spring Boot", 1);
    }

    @Test
    @DisplayName("Prueba para crear una lección")
    void givenLessonRequest_whenCreate_thenReturnLessonResponse() {
        Course dummyCourse = new Course();
        dummyCourse.setId(1L);

        given(courseRepository.findById(anyLong())).willReturn(Optional.of(dummyCourse));
        given(lessonMapper.toEntity(any(LessonRequestDto.class))).willReturn(lesson);
        given(lessonRepository.save(any(Lesson.class))).willReturn(lesson);
        given(lessonMapper.toDto(any(Lesson.class))).willReturn(responseDto);

        LessonResponseDto savedLesson = lessonService.create(requestDto);

        assertThat(savedLesson).isNotNull();
        assertThat(savedLesson.getTitle()).isEqualTo("Introducción a Spring Boot");
        verify(lessonRepository).save(any(Lesson.class));
    }
}