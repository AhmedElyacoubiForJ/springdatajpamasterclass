SELECT * FROM student
join public.student_id_card sic on student.id = sic.student_id;

SELECT * FROM book;

SELECT * FROM book
JOIN student s on book.student_id = s.id;


SELECT * FROM student
join enrollment ON student.id = enrollment.student_id
join course ON enrollment.course_id = course.id;

