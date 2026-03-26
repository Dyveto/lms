package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.StudentRequestDto;
import edu.unimagdalena.lms.dto.response.StudentResponseDto;
import edu.unimagdalena.lms.entities.Student;
import edu.unimagdalena.lms.repositories.StudentRepository;
import edu.unimagdalena.lms.services.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public StudentResponseDto create(StudentRequestDto studentRequestDto) {
        Student student = studentMapper.toEntity(studentRequestDto);
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    @Override
    public StudentResponseDto findById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(studentMapper::toDto).orElse(null);
    }

    @Override
    public List<StudentResponseDto> findAll() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponseDto update(Long id, StudentRequestDto studentRequestDto) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            return null;
        }
        student.setEmail(studentRequestDto.getEmail());
        student.setFullName(studentRequestDto.getFullName());
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    @Override
    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<StudentResponseDto> findStudentsByCourse(Long courseId) {
        return studentRepository.findStudentByCourseId(courseId).stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }
}
