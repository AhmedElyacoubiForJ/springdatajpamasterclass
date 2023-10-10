package edu.yacoubi.springdatajpamasterclass.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

// we don't need that the framework generate for us
// the ManyToMany @JoinTable specified in student s. property courses
// we want to create the table self
@Entity(name = "Enrollment")
@Table(name = "enrollment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Enrollment {

    @EmbeddedId
    private EnrollmentId id;

    @ManyToOne
    @MapsId("studentId") // s. EnrollmentId property class
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("courseId") // s. EnrollmentId property class
    @JoinColumn(name = "course_id")
    private Course course;

    // so we have now a full control of the association table
    // And we can add some properties, instead of joinTable created by the framework
    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"

    )
    private LocalDateTime createdAt;

    public Enrollment(Student student, Course course, LocalDateTime createdAt) {
        this.student = student;
        this.course = course;
        this.createdAt = createdAt;
    }
}
