package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.StudentRequestDto;
import edu.unimagdalena.lms.dto.response.StudentResponseDto;
import java.util.List;

public interface StudentService {
    StudentResponseDto create(StudentRequestDto studentRequestDto);
    StudentResponseDto findById(Long id);
    List<StudentResponseDto> findAll();
    StudentResponseDto update(Long id, StudentRequestDto studentRequestDto);
    void delete(Long id);
    List<StudentResponseDto> findStudentsByCourse(Long courseId);
}
