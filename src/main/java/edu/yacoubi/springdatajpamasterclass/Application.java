package edu.yacoubi.springdatajpamasterclass;

import com.github.javafaker.Faker;
import edu.yacoubi.springdatajpamasterclass.model.Book;
import edu.yacoubi.springdatajpamasterclass.model.Student;
import edu.yacoubi.springdatajpamasterclass.model.StudentIdCard;
import edu.yacoubi.springdatajpamasterclass.repository.StudentIdCardRepository;
import edu.yacoubi.springdatajpamasterclass.repository.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@Slf4j
public class Application {

	@PersistenceContext
	private EntityManager entityManager;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@Bean
	CommandLineRunner commandLineRunner(
			StudentRepository studentRepository,
			StudentIdCardRepository studentIdCardRepository) {

		return args -> {
			// Many to Many relationships in Action

			Faker faker = new Faker();
			Student student = generateStudent(faker);

			StudentIdCard studentIdCard = new StudentIdCard(
					"123456789",
					student
			);

			//
			student.setStudentIdCard(studentIdCard);

			//
			student.addBook(
					new Book("Spring Data JPA", LocalDateTime.now().minusDays(10))
			);
			student.addBook(
					new Book("Clean code", LocalDateTime.now())
			);
			student.addBook(
					new Book("Spring Thymeleaf MVC", LocalDateTime.now().minusYears(1))
			);

			//
			studentRepository.save(student);
			//studentIdCardRepository.save(studentIdCard);

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

	@Service
	@EnableTransactionManagement
	class testService {

		@Transactional(value = Transactional.TxType.MANDATORY)
		public void fetchTest(StudentRepository studentRepository) {
			entityManager.getTransaction().begin();
			studentRepository
					.findById(1L)
					.ifPresent(s -> {
						System.out.println("fetch book lazy...");

						//Hibernate.initialize(s.getBooks());

						List<Book> books = s.getBooks();
						books.forEach(b -> {
							System.out.println(s.getFirstName() + "borrowed " + b.getBookName());
						});
					});
			entityManager.getTransaction().commit();

		}
	}
}
