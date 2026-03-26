package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.CourseRequestDto;
import edu.unimagdalena.lms.dto.response.CourseResponseDto;
import java.util.List;

public interface CourseService {
    CourseResponseDto create(CourseRequestDto courseRequestDto);
    CourseResponseDto findById(Long id);
    List<CourseResponseDto> findAll();
    CourseResponseDto update(Long id, CourseRequestDto courseRequestDto);
    void delete(Long id);
}
