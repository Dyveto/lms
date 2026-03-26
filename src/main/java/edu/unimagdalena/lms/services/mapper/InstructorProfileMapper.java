package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.dto.request.InstructorProfileRequestDto;
import edu.unimagdalena.lms.dto.response.InstructorProfileResponseDto;
import edu.unimagdalena.lms.entities.InstructorProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstructorProfileMapper {

    @Mapping(source = "instructor.id", target = "instructorId")
    InstructorProfileResponseDto toDto(InstructorProfile instructorProfile);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    InstructorProfile toEntity(InstructorProfileRequestDto instructorProfileRequestDto);
}
