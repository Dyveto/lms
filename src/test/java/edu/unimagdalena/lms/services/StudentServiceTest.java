package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.StudentRequestDto;
import edu.unimagdalena.lms.dto.response.StudentResponseDto;
import edu.unimagdalena.lms.entities.Student;
import edu.unimagdalena.lms.repositories.StudentRepository;
import edu.unimagdalena.lms.services.mapper.StudentMapper;
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
class StudentServiceTest {

    @Mock private StudentRepository studentRepository;
    @Mock private StudentMapper studentMapper;

    @InjectMocks private StudentServiceImpl studentService;

    private Student student;
    private StudentRequestDto requestDto;
    private StudentResponseDto responseDto;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setFullName("Juan Pérez");
        student.setEmail("juan.perez@ejemplo.com");

        requestDto = new StudentRequestDto("juan.perez@ejemplo.com", "Juan Pérez");
        
        responseDto = new StudentResponseDto(1L, "juan.perez@ejemplo.com", "Juan Pérez");
    }

    @Test
    @DisplayName("Prueba para crear un estudiante")
    void givenStudentRequest_whenCreate_thenReturnStudentResponse() {
        given(studentMapper.toEntity(any(StudentRequestDto.class))).willReturn(student);
        given(studentRepository.save(any(Student.class))).willReturn(student);
        given(studentMapper.toDto(any(Student.class))).willReturn(responseDto);

        StudentResponseDto savedStudent = studentService.create(requestDto);

        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getFullName()).isEqualTo("Juan Pérez");
        verify(studentRepository).save(any(Student.class));
    }
}