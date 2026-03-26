package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.EnrollmentRequestDto;
import edu.unimagdalena.lms.dto.response.EnrollmentResponseDto;
import java.util.List;

public interface EnrollmentService {
    EnrollmentResponseDto create(EnrollmentRequestDto enrollmentRequestDto);
    EnrollmentResponseDto findById(Long id);
    List<EnrollmentResponseDto> findAll();
    EnrollmentResponseDto update(Long id, EnrollmentRequestDto enrollmentRequestDto);
    void delete(Long id);
}
