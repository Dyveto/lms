package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.dto.request.InstructorRequestDto;
import edu.unimagdalena.lms.dto.response.InstructorResponseDto;
import edu.unimagdalena.lms.entities.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstructorMapper {

    InstructorResponseDto toDto(Instructor instructor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instructorProfile", ignore = true)
    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Instructor toEntity(InstructorRequestDto instructorRequestDto);
}
