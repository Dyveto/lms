package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.InstructorProfileRequestDto;
import edu.unimagdalena.lms.dto.response.InstructorProfileResponseDto;
import java.util.List;

public interface InstructorProfileService {
    InstructorProfileResponseDto create(InstructorProfileRequestDto instructorProfileRequestDto);
    InstructorProfileResponseDto findById(Long id);
    List<InstructorProfileResponseDto> findAll();
    InstructorProfileResponseDto update(Long id, InstructorProfileRequestDto instructorProfileRequestDto);
    void delete(Long id);
}
