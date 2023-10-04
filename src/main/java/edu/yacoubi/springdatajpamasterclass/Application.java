package edu.yacoubi.springdatajpamasterclass;

import edu.yacoubi.springdatajpamasterclass.model.Student;
import edu.yacoubi.springdatajpamasterclass.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
		return args -> {

			Student maria = new Student(
					"Maria",
					"Jones",
					"maria.jones@yacoubi.edu",
					25
			);

			Student maria2 = new Student(
					"Maria",
					"Jones",
					"maria2.jones@yacoubi.edu",
					21
			);

			Student ahmed = new Student(
					"Ahmed",
					"Ali",
					"ahmed.ali@yacoubi.edu",
					22
			);
			System.out.println("Adding maria & ahmed");
			studentRepository.saveAll(List.of(maria,ahmed, maria2));

			studentRepository
					.findStudentByEmail("ahmed.ali@yacoubi.edu")
					.ifPresentOrElse(
							System.out::println,
							() -> System.out.println("Student with email ahmed.ali@yacoubi.edu not found")
					);

			System.out.println("findStudentsByFirstNameEqualsAndAgeEquals: Maria, 25");
			studentRepository.findStudentsByFirstNameEqualsAndAgeEquals(
					"Maria",
					25
			).forEach(System.out::println);

			System.out.println();

			System.out.println("findStudentsByFirstNameEqualsAndAgeIsGreaterThan: Maria, 21");
			studentRepository.findStudentsByFirstNameEqualsAndAgeIsGreaterThan(
					"Maria", 21
			).forEach(System.out::println);

			System.out.println();

			System.out.println("findStudentsByFirstNameEqualsAndAgeIsGreaterThanEqual: Maria, 21");
			studentRepository.findStudentsByFirstNameEqualsAndAgeIsGreaterThanEqual(
					"Maria", 21
			).forEach(System.out::println);

		};
	}

}
