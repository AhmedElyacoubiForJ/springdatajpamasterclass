package edu.yacoubi.springdatajpamasterclass.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

// we don't need that the framework generate for us
// the ManyToMany @JoinTable specified in student s. property courses
// we want to create the table self
@Entity(name = "Enrollment")
@Table(name = "enrollment")
@NoArgsConstructor
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

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
    }
}
