package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.dto.request.AssessmentRequestDto;
import edu.unimagdalena.lms.dto.response.AssessmentResponseDto;
import edu.unimagdalena.lms.entities.Assessment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssessmentMapper {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "course.id", target = "courseId")
    AssessmentResponseDto toDto(Assessment assessment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "course", ignore = true)
    Assessment toEntity(AssessmentRequestDto assessmentRequestDto);
}
