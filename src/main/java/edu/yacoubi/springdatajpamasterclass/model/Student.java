package edu.yacoubi.springdatajpamasterclass.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Student") // name to reference to it in JPQL
@Table(
        name = "student",
        uniqueConstraints = {
              @UniqueConstraint(
                      name = "student_email_unique",
                      columnNames = "email"
              )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Student {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "student_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;

    @Column(
            name = "email",
            nullable = false,
            //unique = true, to overwrite the generated unique name. s. Table def.
            columnDefinition = "TEXT"
    )
    private String email;

    @Column(
            name = "age",
            nullable = false
    )
    private Integer age;

    // if we want a bidirectional relation
    // we can specify these
    // refer to property in StudentIdCard
    // when we load a Student, a StudentIdCard will be loaded
    // we have specified 2 ways to get entities s. StudentIdCard also
    @OneToOne(
            mappedBy = "student",
            // if we want to delete a student,
            // so the StudentIdCard associated with him must be also deleted.
            // Cause for the foreign key constraints
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private StudentIdCard studentIdCard;

    @OneToMany(
            // s. Book property name (mapping owner)
            mappedBy = "student",
            // means when we delete a student we delete the book association with it
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private final List<Book> books = new ArrayList<>();

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY // is per default
    )
    @JoinTable(
            name = "enrollment",
            joinColumns = @JoinColumn(
                    name = "student_id",
                    foreignKey = @ForeignKey(name = "enrolment_student_id_fk")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "course_id",
                    foreignKey = @ForeignKey(name = "enrolment_course_id_fk")
            )
    )
    private List<Course> courses = new ArrayList<>();

    public Student(String firstName,
                       String lastName,
                       String email,
                       Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public void addBook(Book book) {
        if (!this.books.contains(book)) {
            this.books.add(book);
            book.setStudent(this);
        }
    }

    public void removeBook(Book book) {
        if (this.books.contains(book)) {
            this.books.remove(book);
            book.setStudent(null);
        }
    }

    public void enrolToCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
            course.getStudents().add(this);
        }
    }

    public void unEnrolToCourse(Course course) {
        if (!courses.contains(course)) {
            courses.remove(course);
            course.getStudents().remove(this);
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
