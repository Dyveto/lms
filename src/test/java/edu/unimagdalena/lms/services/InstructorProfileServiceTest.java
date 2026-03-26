package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.dto.request.InstructorProfileRequestDto;
import edu.unimagdalena.lms.dto.response.InstructorProfileResponseDto;
import edu.unimagdalena.lms.entities.Instructor;
import edu.unimagdalena.lms.entities.InstructorProfile;
import edu.unimagdalena.lms.repositories.InstructorProfileRepository;
import edu.unimagdalena.lms.repositories.InstructorRepository;
import edu.unimagdalena.lms.services.mapper.InstructorProfileMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InstructorProfileServiceTest {

    @Mock private InstructorProfileRepository profileRepository;
    @Mock private InstructorRepository instructorRepository;
    @Mock private InstructorProfileMapper profileMapper;

    @InjectMocks private InstructorProfileServiceImpl profileService;

    private InstructorProfile profile;
    private InstructorProfileRequestDto requestDto;
    private InstructorProfileResponseDto responseDto;

    @BeforeEach
    void setUp() {
        profile = new InstructorProfile();
        profile.setId(1L);
        profile.setBio("Experta en bases de datos y backend.");
        profile.setPhone("+573001234567");

        requestDto = new InstructorProfileRequestDto(1L, "+573001234567", "Experta en bases de datos y backend.");
        
        responseDto = new InstructorProfileResponseDto(1L, 1L, "+573001234567", "Experta en bases de datos y backend.");
    }

    @Test
    @DisplayName("Prueba para crear un perfil de instructor")
    void givenProfileRequest_whenCreate_thenReturnProfileResponse() {
        Instructor dummyInstructor = new Instructor();
        dummyInstructor.setId(1L);

        given(instructorRepository.findById(anyLong())).willReturn(Optional.of(dummyInstructor));
        
        given(profileMapper.toEntity(any(InstructorProfileRequestDto.class))).willReturn(profile);
        given(profileRepository.save(any(InstructorProfile.class))).willReturn(profile);
        given(profileMapper.toDto(any(InstructorProfile.class))).willReturn(responseDto);

        InstructorProfileResponseDto savedProfile = profileService.create(requestDto);

        assertThat(savedProfile).isNotNull();
        assertThat(savedProfile.getBio()).isEqualTo("Experta en bases de datos y backend.");
        verify(profileRepository).save(any(InstructorProfile.class));
    }
}