package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.InstructorRequestDto;
import edu.unimagdalena.lms.dto.response.InstructorResponseDto;
import edu.unimagdalena.lms.entities.Instructor;
import edu.unimagdalena.lms.repositories.InstructorRepository;
import edu.unimagdalena.lms.services.mapper.InstructorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private InstructorMapper instructorMapper;

    @Override
    public InstructorResponseDto create(InstructorRequestDto instructorRequestDto) {
        Instructor instructor = instructorMapper.toEntity(instructorRequestDto);
        instructor = instructorRepository.save(instructor);
        return instructorMapper.toDto(instructor);
    }

    @Override
    public InstructorResponseDto findById(Long id) {
        Optional<Instructor> instructor = instructorRepository.findById(id);
        return instructor.map(instructorMapper::toDto).orElse(null);
    }

    @Override
    public List<InstructorResponseDto> findAll() {
        return instructorRepository.findAll().stream()
                .map(instructorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InstructorResponseDto update(Long id, InstructorRequestDto instructorRequestDto) {
        Instructor instructor = instructorRepository.findById(id).orElse(null);
        if (instructor == null) {
            return null;
        }
        instructor.setEmail(instructorRequestDto.getEmail());
        instructor.setFullName(instructorRequestDto.getFullName());
        instructor = instructorRepository.save(instructor);
        return instructorMapper.toDto(instructor);
    }

    @Override
    public void delete(Long id) {
        instructorRepository.deleteById(id);
    }
}
