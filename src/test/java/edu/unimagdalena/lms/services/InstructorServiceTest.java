package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.InstructorRequestDto;
import edu.unimagdalena.lms.dto.response.InstructorResponseDto;
import edu.unimagdalena.lms.entities.Instructor;
import edu.unimagdalena.lms.repositories.InstructorRepository;
import edu.unimagdalena.lms.services.mapper.InstructorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InstructorServiceTest {

    @Mock private InstructorRepository instructorRepository;
    @Mock private InstructorMapper instructorMapper;

    @InjectMocks private InstructorServiceImpl instructorService;

    private Instructor instructor;
    private InstructorRequestDto requestDto;
    private InstructorResponseDto responseDto;

    @BeforeEach
    void setUp() {
        instructor = new Instructor();
        instructor.setId(1L);
        instructor.setFullName("María Gómez");
        instructor.setEmail("maria.gomez@ejemplo.com");

        // FIXED: Swapped so email is first, fullName is second
        requestDto = new InstructorRequestDto("maria.gomez@ejemplo.com", "María Gómez");
        
        // FIXED: Swapped so email is second, fullName is third
        responseDto = new InstructorResponseDto(1L, "maria.gomez@ejemplo.com", "María Gómez");
    }

    @Test
    @DisplayName("Prueba para crear un instructor")
    void givenInstructorRequest_whenCreate_thenReturnInstructorResponse() {
        given(instructorMapper.toEntity(any(InstructorRequestDto.class))).willReturn(instructor);
        given(instructorRepository.save(any(Instructor.class))).willReturn(instructor);
        given(instructorMapper.toDto(any(Instructor.class))).willReturn(responseDto);

        InstructorResponseDto savedInstructor = instructorService.create(requestDto);

        assertThat(savedInstructor).isNotNull();
        // Updated to standard getter
        assertThat(savedInstructor.getFullName()).isEqualTo("María Gómez");
        verify(instructorRepository).save(any(Instructor.class));
    }
}