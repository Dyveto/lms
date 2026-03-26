package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.dto.request.EnrollmentRequestDto;
import edu.unimagdalena.lms.dto.response.EnrollmentResponseDto;
import edu.unimagdalena.lms.entities.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "course.id", target = "courseId")
    EnrollmentResponseDto toDto(Enrollment enrollment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "course", ignore = true)
    Enrollment toEntity(EnrollmentRequestDto enrollmentRequestDto);
}
