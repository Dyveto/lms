package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.LessonRequestDto;
import edu.unimagdalena.lms.dto.response.LessonResponseDto;
import java.util.List;

public interface LessonService {
    LessonResponseDto create(LessonRequestDto lessonRequestDto);
    LessonResponseDto findById(Long id);
    List<LessonResponseDto> findAll();
    LessonResponseDto update(Long id, LessonRequestDto lessonRequestDto);
    void delete(Long id);
}
