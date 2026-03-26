package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.AssessmentRequestDto;
import edu.unimagdalena.lms.dto.response.AssessmentResponseDto;
import java.util.List;

public interface AssessmentService {
    AssessmentResponseDto create(AssessmentRequestDto assessmentRequestDto);
    AssessmentResponseDto findById(Long id);
    List<AssessmentResponseDto> findAll();
    AssessmentResponseDto update(Long id, AssessmentRequestDto assessmentRequestDto);
    void delete(Long id);
}
