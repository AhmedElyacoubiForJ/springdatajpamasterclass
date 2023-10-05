package edu.yacoubi.springdatajpamasterclass.repository;

import edu.yacoubi.springdatajpamasterclass.model.StudentIdCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentIdCardRepository
        extends CrudRepository<StudentIdCard, Long> {
}
