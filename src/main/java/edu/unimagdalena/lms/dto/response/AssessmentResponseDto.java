package edu.unimagdalena.lms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentResponseDto {
    private Long id;
    private Long studentId;
    private Long courseId;
    private String type;
    private int score;
    private Instant takenAt;
}
