package edu.yacoubi.springdatajpamasterclass;

import com.github.javafaker.Faker;
import edu.yacoubi.springdatajpamasterclass.model.Book;
import edu.yacoubi.springdatajpamasterclass.model.Student;
import edu.yacoubi.springdatajpamasterclass.model.StudentIdCard;
import edu.yacoubi.springdatajpamasterclass.repository.StudentIdCardRepository;
import edu.yacoubi.springdatajpamasterclass.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
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

			// TODO later saving books through repository

			System.out.println();

			// to learn about data fetching
			// books will not be loaded at first
			// because the fetching type by many to one
			// is lazy per default
			studentRepository
					.findById(1L)
					.ifPresent(System.out::println);

			System.out.println();

			// but what we can do this, using the getter
			// to get the books

			// don't work at this point, because the session close after findById
//			studentRepository
//					.findById(1L)
//					.ifPresent(s -> {
//						System.out.println("fetch book lazy...");
//						List<Book> books = s.getBooks();
//
//						books.forEach(b -> {
//							System.out.println(s.getFirstName() + "borrowed " + b.getBookName());
//						});
//					});

			// first try to keep session open, but don't working
			new testService().fetchTest(studentRepository);
			
			/*
			* Caused by: org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: edu.yacoubi.springdatajpamasterclass.model.Student.books, could not initialize proxy - no Session
	at org.hibernate.collection.internal.AbstractPersistentCollection.throwLazyInitializationException(AbstractPersistentCollection.java:614) ~[hibernate-core-5.6.15.Final.jar:5.6.15.Final]
	at org.hibernate.collection.internal.AbstractPersistentCollection.withTemporarySessionIfNeeded(AbstractPersistentCollection.java:218) ~[hibernate-core-5.6.15.Final.jar:5.6.15.Final]
	at org.hibernate.collection.internal.AbstractPersistentCollection.initialize(AbstractPersistentCollection.java:591) ~[hibernate-core-5.6.15.Final.jar:5.6.15.Final]
	at org.hibernate.collection.internal.AbstractPersistentCollection.read(AbstractPersistentCollection.java:149) ~[hibernate-core-5.6.15.Final.jar:5.6.15.Final]
	at org.hibernate.collection.internal.PersistentBag.iterator(PersistentBag.java:387) ~[hibernate-core-5.6.15.Final.jar:5.6.15.Final]
			* */

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
	class testService {
		@Transactional
		public void fetchTest(StudentRepository studentRepository) {
			studentRepository
					.findById(1L)
					.ifPresent(s -> {
						System.out.println("fetch book lazy...");
						List<Book> books = s.getBooks();

						books.forEach(b -> {
							System.out.println(s.getFirstName() + "borrowed " + b.getBookName());
						});
					});
		}

	}
}
