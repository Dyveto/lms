package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.AssessmentRequestDto;
import edu.unimagdalena.lms.dto.response.AssessmentResponseDto;
import edu.unimagdalena.lms.entities.Assessment;
import edu.unimagdalena.lms.repositories.AssessmentRepository;
import edu.unimagdalena.lms.repositories.CourseRepository;
import edu.unimagdalena.lms.repositories.StudentRepository;
import edu.unimagdalena.lms.services.mapper.AssessmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssessmentServiceImpl implements AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AssessmentMapper assessmentMapper;

    @Override
    public AssessmentResponseDto create(AssessmentRequestDto assessmentRequestDto) {
        Assessment assessment = assessmentMapper.toEntity(assessmentRequestDto);
        studentRepository.findById(assessmentRequestDto.getStudentId()).ifPresent(assessment::setStudent);
        courseRepository.findById(assessmentRequestDto.getCourseId()).ifPresent(assessment::setCourse);
        assessment = assessmentRepository.save(assessment);
        return assessmentMapper.toDto(assessment);
    }

    @Override
    public AssessmentResponseDto findById(Long id) {
        Optional<Assessment> assessment = assessmentRepository.findById(id);
        return assessment.map(assessmentMapper::toDto).orElse(null);
    }

    @Override
    public List<AssessmentResponseDto> findAll() {
        return assessmentRepository.findAll().stream()
                .map(assessmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AssessmentResponseDto update(Long id, AssessmentRequestDto assessmentRequestDto) {
        Assessment assessment = assessmentRepository.findById(id).orElse(null);
        if (assessment == null) {
            return null;
        }
        studentRepository.findById(assessmentRequestDto.getStudentId()).ifPresent(assessment::setStudent);
        courseRepository.findById(assessmentRequestDto.getCourseId()).ifPresent(assessment::setCourse);
        assessment.setType(assessmentRequestDto.getType());
        assessment.setScore(assessmentRequestDto.getScore());
        assessment.setTakenAt(assessmentRequestDto.getTakenAt());
        assessment = assessmentRepository.save(assessment);
        return assessmentMapper.toDto(assessment);
    }

    @Override
    public void delete(Long id) {
        assessmentRepository.deleteById(id);
    }
}
