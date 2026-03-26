package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.dto.request.CourseRequestDto;
import edu.unimagdalena.lms.dto.response.CourseResponseDto;
import edu.unimagdalena.lms.entities.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(source = "instructor.id", target = "instructorId")
    CourseResponseDto toDto(Course course);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    @Mapping(target = "lessons", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "assessments", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Course toEntity(CourseRequestDto courseRequestDto);
}
