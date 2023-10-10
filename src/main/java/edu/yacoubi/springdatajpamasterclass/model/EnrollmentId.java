package edu.yacoubi.springdatajpamasterclass.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

// class that represent a composite key
// in our case it will be the
// association between student and course
// respectively enrollment.
// EnrollmentId will be embedded in Enrollment
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data // to serve as composite key equals & hashcode is needed
public class EnrollmentId implements Serializable { // must be serializable

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "course_id")
    private Long courseId;
}
