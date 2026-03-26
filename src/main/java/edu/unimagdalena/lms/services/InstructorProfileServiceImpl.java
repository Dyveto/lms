package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.InstructorProfileRequestDto;
import edu.unimagdalena.lms.dto.response.InstructorProfileResponseDto;
import edu.unimagdalena.lms.entities.InstructorProfile;
import edu.unimagdalena.lms.repositories.InstructorProfileRepository;
import edu.unimagdalena.lms.repositories.InstructorRepository;
import edu.unimagdalena.lms.services.mapper.InstructorProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstructorProfileServiceImpl implements InstructorProfileService {

    @Autowired
    private InstructorProfileRepository instructorProfileRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private InstructorProfileMapper instructorProfileMapper;

    @Override
    public InstructorProfileResponseDto create(InstructorProfileRequestDto instructorProfileRequestDto) {
        InstructorProfile instructorProfile = instructorProfileMapper.toEntity(instructorProfileRequestDto);
        instructorRepository.findById(instructorProfileRequestDto.getInstructorId()).ifPresent(instructorProfile::setInstructor);
        instructorProfile = instructorProfileRepository.save(instructorProfile);
        return instructorProfileMapper.toDto(instructorProfile);
    }

    @Override
    public InstructorProfileResponseDto findById(Long id) {
        Optional<InstructorProfile> instructorProfile = instructorProfileRepository.findById(id);
        return instructorProfile.map(instructorProfileMapper::toDto).orElse(null);
    }

    @Override
    public List<InstructorProfileResponseDto> findAll() {
        return instructorProfileRepository.findAll().stream()
                .map(instructorProfileMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InstructorProfileResponseDto update(Long id, InstructorProfileRequestDto instructorProfileRequestDto) {
        InstructorProfile instructorProfile = instructorProfileRepository.findById(id).orElse(null);
        if (instructorProfile == null) {
            return null;
        }
        instructorRepository.findById(instructorProfileRequestDto.getInstructorId()).ifPresent(instructorProfile::setInstructor);
        instructorProfile.setPhone(instructorProfileRequestDto.getPhone());
        instructorProfile.setBio(instructorProfileRequestDto.getBio());
        instructorProfile = instructorProfileRepository.save(instructorProfile);
        return instructorProfileMapper.toDto(instructorProfile);
    }

    @Override
    public void delete(Long id) {
        instructorProfileRepository.deleteById(id);
    }
}
