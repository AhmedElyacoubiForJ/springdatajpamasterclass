package edu.yacoubi.springdatajpamasterclass.repository;

import edu.yacoubi.springdatajpamasterclass.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // we can define a custom method query (as apart from a predefined queries)
    // for example how to find someone with a given email.
    // The method names follow naming convention
    // and the framework will generate for us the sql query.
    // But we can overwrite it, when want to have a full control above the sql,
    // so we can annotate the method query w. @Query.(and in these case the method name convention doesn't matter)
    // In these case serves only how to do it.
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    // the method query will work also without the
    // annotation. (edu)
    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 AND s.age = ?2")
    List<Student> findStudentsByFirstNameEqualsAndAgeEquals(
            String firstName,
            Integer age
    );

    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 AND s.age > ?2")
    List<Student> findStudentsByFirstNameEqualsAndAgeIsGreaterThan(
            String firstName,
            Integer age
    );

    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 AND s.age >= ?2")
    List<Student> findStudentsByFirstNameEqualsAndAgeIsGreaterThanEqual(
            String firstName,
            Integer age
    );

    // SELECT * FROM student  WHERE first_name = 'Maria' AND age >= 22;
    @Query(
            value = "SELECT * FROM student  WHERE first_name = ?1 AND age >= ?2",
            nativeQuery = true
    )
    List<Student> findStudentsByFirstNameEqualsAndAgeIsGreaterThanEqualNative(
            String firstName,
            Integer age
    );
}
