package edu.yacoubi.springdatajpamasterclass;

import com.fasterxml.jackson.core.JsonToken;
import com.github.javafaker.Faker;
import edu.yacoubi.springdatajpamasterclass.model.Student;
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
	CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
		return args -> {
			generateRandomStudents(studentRepository);

			System.out.println();

			// get students by firstName sorted desc
			Sort byFirstNameAscSort = Sort.by(
					Sort.Direction.ASC,
					"firstName"
			);

			System.out.println("Sorting students by firstname ascending");
			studentRepository
					.findAll(byFirstNameAscSort)
					.forEach(s -> System.out.println(s.getFirstName()));

			System.out.println();

            Sort byAgeDescSort = Sort.by("age").descending();
            Sort byFirstNameAscAndAgeDescSort = Sort
                    .by("firstName").ascending()
					.and(byAgeDescSort);

			System.out.println("Sorting students by firstname ascending and age descending");
			studentRepository
					.findAll(byFirstNameAscAndAgeDescSort)
					.forEach(s -> System.out.println(s.getFirstName() + " "+ s.getAge()));

			System.out.println();

			System.out.println("Pagination and sorting");
			for (int i=0; i<4; i++) {
				System.out.println();
				System.out.println("Page : " + (i + 1));
				PageRequest pageable = PageRequest.of(
						i,
						5,
						byFirstNameAscSort
				);
				Page<Student> studentPage = studentRepository.findAll(pageable);
				studentPage.forEach(System.out::println);
			}

		};
	}

	private static void generateRandomStudents(StudentRepository studentRepository) {
		Faker faker = new Faker();
		for (int i = 0; i<20;i++){
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
			studentRepository.save(student);
		}
	}

}
