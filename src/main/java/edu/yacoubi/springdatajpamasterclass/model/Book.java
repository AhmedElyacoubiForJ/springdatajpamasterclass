package edu.yacoubi.springdatajpamasterclass.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Book")
@Table(name = "book")
@Getter @Setter @NoArgsConstructor
public class Book {
    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "book_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "book_name",
            nullable = false
    )
    private String bookName;

    // many books can belong to one student
    // That was a unidirectional implementation
    // depending on the application scenarios
    // We can also implement a bidirectional relationship
    // And now lets go a head and do it. s. Student entity
    @ManyToOne
    @JoinColumn(
            name = "student_id",
            nullable = false,
            referencedColumnName = "id",
            // readability
            // to rename a generated random name created by spring data framework
            // so, it's the best practice to have a full control about our application
            foreignKey = @ForeignKey(
                    name = "student_book_fk"
            )
    )
    private Student student;

    public Book(
            LocalDateTime createdAt,
            String bookName,
            Student student) {
        this.createdAt = createdAt;
        this.bookName = bookName;
        this.student = student;
    }

    public Book(
            String bookName,
            LocalDateTime createdAt) {
        this.createdAt = createdAt;
        this.bookName = bookName;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", bookName='" + bookName + '\'' +
                ", student=" + student +
                '}';
    }
}
