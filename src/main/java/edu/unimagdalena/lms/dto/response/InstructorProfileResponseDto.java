package edu.unimagdalena.lms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstructorProfileResponseDto {
    private Long id;
    private Long instructorId;
    private String phone;
    private String bio;
}
