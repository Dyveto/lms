package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.dto.request.StudentRequestDto;
import edu.unimagdalena.lms.dto.response.StudentResponseDto;
import edu.unimagdalena.lms.entities.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentResponseDto toDto(Student student);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "assessments", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Student toEntity(StudentRequestDto studentRequestDto);
}
