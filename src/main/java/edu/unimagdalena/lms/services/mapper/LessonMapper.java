package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.dto.request.LessonRequestDto;
import edu.unimagdalena.lms.dto.response.LessonResponseDto;
import edu.unimagdalena.lms.entities.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    @Mapping(source = "course.id", target = "courseId")
    LessonResponseDto toDto(Lesson lesson);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    Lesson toEntity(LessonRequestDto lessonRequestDto);
}
