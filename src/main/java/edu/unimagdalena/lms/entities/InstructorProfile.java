package edu.unimagdalena.lms.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "instructors_profiles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class InstructorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    private String phone;

    private String bio;
}
