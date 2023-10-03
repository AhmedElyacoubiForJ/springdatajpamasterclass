package edu.yacoubi.springdatajpamasterclass.repository;

import edu.yacoubi.springdatajpamasterclass.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
