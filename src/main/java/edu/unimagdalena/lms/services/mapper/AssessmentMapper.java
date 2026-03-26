package edu.unimagdalena.lms.services.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import edu.unimagdalena.lms.dto.request.AssessmentRequestDto;
import edu.unimagdalena.lms.dto.response.AssessmentResponseDto;
import edu.unimagdalena.lms.entities.Assessment;

@Mapper(componentModel = "spring")
public interface AssessmentMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "takenAt", ignore = true)
    Assessment toEntity(AssessmentRequestDto assessmentRequestDto);

    AssessmentResponseDto toDto(Assessment assessment);
}