package edu.yacoubi.springdatajpamasterclass;

import com.fasterxml.jackson.core.JsonToken;
import com.github.javafaker.Faker;
import edu.yacoubi.springdatajpamasterclass.model.Student;
import edu.yacoubi.springdatajpamasterclass.model.StudentIdCard;
import edu.yacoubi.springdatajpamasterclass.repository.StudentIdCardRepository;
import edu.yacoubi.springdatajpamasterclass.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootApplication
@Slf4j
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@Bean
	CommandLineRunner commandLineRunner(
			StudentRepository studentRepository,
			StudentIdCardRepository studentIdCardRepository) {

		return args -> {
			Faker faker = new Faker();
			Student student = generateStudent(faker);;
			StudentIdCard studentIdCard = new StudentIdCard(
					"123456789",
					student
			);
			studentIdCardRepository.save(studentIdCard);
		};
	}

	private static Student generateStudent(Faker faker) {
		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		String email = String.format("%s.%s@yacoubi.edu", firstName, lastName);
		int age = faker.number().numberBetween(17, 55);

		Student student = new Student(
				firstName,
				lastName,
				email,
				age
		);
		return student;
	}

	private static void generateRandomStudents(StudentRepository studentRepository) {
		Faker faker = new Faker();
		for (int i = 0; i<20;i++){
			studentRepository.save(generateStudent(faker));
		}
	}
}
