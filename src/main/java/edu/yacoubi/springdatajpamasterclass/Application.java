package edu.yacoubi.springdatajpamasterclass;

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

import java.util.Optional;

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

			System.out.println();

			System.out.println("studentIdCardRepository.findById(1L).ifPresent(System.out::println)");
			studentIdCardRepository.findById(1L).ifPresent(System.out::println);

			System.out.println();

			System.out.println("studentRepository.findById(1L).ifPresent(System.out::println)");
			Optional<Student> optionalStudent = studentRepository.findById(1L);
			optionalStudent.ifPresent(System.out::println);
			System.out.println(optionalStudent.get().getStudentIdCard());

			System.out.println();

			studentRepository.deleteById(1L);
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
