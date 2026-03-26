package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.LessonRequestDto;
import edu.unimagdalena.lms.dto.response.LessonResponseDto;
import edu.unimagdalena.lms.entities.Lesson;
import edu.unimagdalena.lms.repositories.CourseRepository;
import edu.unimagdalena.lms.repositories.LessonRepository;
import edu.unimagdalena.lms.services.mapper.LessonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonMapper lessonMapper;

    @Override
    public LessonResponseDto create(LessonRequestDto lessonRequestDto) {
        Lesson lesson = lessonMapper.toEntity(lessonRequestDto);
        courseRepository.findById(lessonRequestDto.getCourseId()).ifPresent(lesson::setCourse);
        lesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(lesson);
    }

    @Override
    public LessonResponseDto findById(Long id) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        return lesson.map(lessonMapper::toDto).orElse(null);
    }

    @Override
    public List<LessonResponseDto> findAll() {
        return lessonRepository.findAll().stream()
                .map(lessonMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LessonResponseDto update(Long id, LessonRequestDto lessonRequestDto) {
        Lesson lesson = lessonRepository.findById(id).orElse(null);
        if (lesson == null) {
            return null;
        }
        lesson.setTitle(lessonRequestDto.getTitle());
        courseRepository.findById(lessonRequestDto.getCourseId()).ifPresent(lesson::setCourse);
        lesson.setOrderIndex(lessonRequestDto.getOrderIndex());
        lesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(lesson);
    }

    @Override
    public void delete(Long id) {
        lessonRepository.deleteById(id);
    }
}
