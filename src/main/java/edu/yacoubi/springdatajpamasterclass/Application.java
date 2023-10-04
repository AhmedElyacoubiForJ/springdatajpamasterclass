package edu.yacoubi.springdatajpamasterclass;

import com.github.javafaker.Faker;
import edu.yacoubi.springdatajpamasterclass.model.Student;
import edu.yacoubi.springdatajpamasterclass.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;

@SpringBootApplication
@Slf4j
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
		return args -> {
			generateRandomStudents(studentRepository);

			System.out.println();

			// get students by firstName sorted desc
			Sort sort = Sort.by(
					Sort.Direction.ASC,
					"firstName"
			);

			System.out.println("Sorting students by firstname asc");
			studentRepository
					.findAll(sort)
					.forEach(student -> System.out.println(student.getFirstName()));

			System.out.println();

			sort = Sort
					.by("firstName")
					.ascending()
					.and(Sort.by("age").descending());
			System.out.println("Sorting students by firstname asc and age des");
			studentRepository
					.findAll(sort)
					.forEach(student -> System.out.println(student.getFirstName() + " "+ student.getAge()));
		};
	}

	private static void generateRandomStudents(StudentRepository studentRepository) {
		Faker faker = new Faker();
		for (int i = 0; i<20;i++){
			String firstName = firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = String.format("%s.%s@yacoubi.edu", firstName, lastName);
			Student student = new Student(
					firstName,
					lastName,
					email,
					faker.number().numberBetween(17, 55)
			);
			studentRepository.save(student);
		}
	}

}
