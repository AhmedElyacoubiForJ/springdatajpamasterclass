package edu.yacoubi.springdatajpamasterclass.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

// @Embeddable
@Embeddable
@NoArgsConstructor @AllArgsConstructor
@Data
public class EnrollmentId implements Serializable {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "course_id")
    private Long courseId;
}
