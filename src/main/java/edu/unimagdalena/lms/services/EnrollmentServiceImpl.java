package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.EnrollmentRequestDto;
import edu.unimagdalena.lms.dto.response.EnrollmentResponseDto;
import edu.unimagdalena.lms.entities.Enrollment;
import edu.unimagdalena.lms.repositories.CourseRepository;
import edu.unimagdalena.lms.repositories.EnrollmentRepository;
import edu.unimagdalena.lms.repositories.StudentRepository;
import edu.unimagdalena.lms.services.mapper.EnrollmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentMapper enrollmentMapper;

    @Override
    public EnrollmentResponseDto create(EnrollmentRequestDto enrollmentRequestDto) {
        Enrollment enrollment = enrollmentMapper.toEntity(enrollmentRequestDto);
        studentRepository.findById(enrollmentRequestDto.getStudentId()).ifPresent(enrollment::setStudent);
        courseRepository.findById(enrollmentRequestDto.getCourseId()).ifPresent(enrollment::setCourse);
        enrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    public EnrollmentResponseDto findById(Long id) {
        Optional<Enrollment> enrollment = enrollmentRepository.findById(id);
        return enrollment.map(enrollmentMapper::toDto).orElse(null);
    }

    @Override
    public List<EnrollmentResponseDto> findAll() {
        return enrollmentRepository.findAll().stream()
                .map(enrollmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EnrollmentResponseDto update(Long id, EnrollmentRequestDto enrollmentRequestDto) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElse(null);
        if (enrollment == null) {
            return null;
        }
        studentRepository.findById(enrollmentRequestDto.getStudentId()).ifPresent(enrollment::setStudent);
        courseRepository.findById(enrollmentRequestDto.getCourseId()).ifPresent(enrollment::setCourse);
        enrollment.setStatus(enrollmentRequestDto.getStatus());
        enrollment.setEnrolledAt(enrollmentRequestDto.getEnrolledAt());
        enrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    public void delete(Long id) {
        enrollmentRepository.deleteById(id);
    }
}
