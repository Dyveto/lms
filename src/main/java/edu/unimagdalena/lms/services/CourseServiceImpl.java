package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.CourseRequestDto;
import edu.unimagdalena.lms.dto.response.CourseResponseDto;
import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.repositories.CourseRepository;
import edu.unimagdalena.lms.repositories.InstructorRepository;
import edu.unimagdalena.lms.services.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public CourseResponseDto create(CourseRequestDto courseRequestDto) {
        Course course = courseMapper.toEntity(courseRequestDto);
        instructorRepository.findById(courseRequestDto.getInstructorId()).ifPresent(course::setInstructor);
        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    @Override
    public CourseResponseDto findById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        return course.map(courseMapper::toDto).orElse(null);
    }

    @Override
    public List<CourseResponseDto> findAll() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponseDto update(Long id, CourseRequestDto courseRequestDto) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            return null;
        }
        course.setTitle(courseRequestDto.getTitle());
        course.setStatus(courseRequestDto.getStatus());
        course.setActive(courseRequestDto.isActive());
        instructorRepository.findById(courseRequestDto.getInstructorId()).ifPresent(course::setInstructor);
        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    @Override
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }
}
