package edu.yacoubi.springdatajpamasterclass.repository;

import edu.yacoubi.springdatajpamasterclass.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // we can define a custom query (as apart from a predefined queries)
    // as example how to find someone with a given email
    // and a methods name follows the naming convention
    // so the framework will generate for us the Query
    Optional<Student> findStudentByEmail(String email);

    List<Student> findStudentsByFirstNameEqualsAndAgeEquals(
            String firstName,
            Integer age
    );

    List<Student> findStudentsByFirstNameEqualsAndAgeIsGreaterThan(
            String firstName,
            Integer age
    );

    List<Student> findStudentsByFirstNameEqualsAndAgeIsGreaterThanEqual(
            String firstName,
            Integer age
    );
}
