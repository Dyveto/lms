package edu.unimagdalena.lms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonRequestDto {
    private Long courseId;
    private String title;
    private int orderIndex;
}
