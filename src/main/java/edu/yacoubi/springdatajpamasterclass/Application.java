package edu.yacoubi.springdatajpamasterclass;

import edu.yacoubi.springdatajpamasterclass.model.Student;
import edu.yacoubi.springdatajpamasterclass.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

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

			Student ahmed = new Student(
					"Ahmed",
					"Ali",
					"ahmed.ali@yacoubi.edu",
					22
			);
			System.out.println("Adding maria & ahmed");
			studentRepository.saveAll(List.of(maria,ahmed));

			System.out.print("count number of students : ");
			System.out.println(studentRepository.count());

			System.out.print("Find student with ID 2 : ");
			studentRepository
					.findById(2L)
					.ifPresentOrElse(
							System.out::println,
							() -> System.out.println("Student with ID 2 not found")
					);

			System.out.println();

			System.out.print("Find student with ID 3 : ");
			studentRepository
					.findById(3L)
					.ifPresentOrElse(
							System.out::println,
							() -> System.out.println("Student with ID 2 not found")
					);

			System.out.println();

			System.out.println("Find all students :");
			List<Student> students = studentRepository.findAll();
			students.forEach(System.out::println);

			System.out.println("Delete student with ID 1: ");
			studentRepository.deleteById(1L);
			System.out.print("count number of students : ");
			System.out.println( studentRepository.count() );
		};
	}

}
