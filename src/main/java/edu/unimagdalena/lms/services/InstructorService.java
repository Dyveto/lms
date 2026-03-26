package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.InstructorRequestDto;
import edu.unimagdalena.lms.dto.response.InstructorResponseDto;
import java.util.List;

public interface InstructorService {
    InstructorResponseDto create(InstructorRequestDto instructorRequestDto);
    InstructorResponseDto findById(Long id);
    List<InstructorResponseDto> findAll();
    InstructorResponseDto update(Long id, InstructorRequestDto instructorRequestDto);
    void delete(Long id);
}
