-- INSERT INTO
-- 	Language
-- VALUES
-- 	("English"),
-- 	("Spanish"),
-- 	("French"),
-- 	("German"),
-- 	("Hindu"),
-- 	("Chinese");
--
-- INSERT INTO
-- 	Course
-- VALUES
-- 	("CS46A", "Introduction to Programming"),
-- 	("CS46B", "Introduction to Data Structures"),
-- 	("CS146", "Data Structures and Algorithms"),
-- 	("CS160", "Software Engineering"),
-- 	("MATH42", "Discrete Mathematics"),
-- 	(
-- 		"MATH161A",
-- 		"Applied Probability and Statistics I"
-- 	),
-- 	("BIOL30", "Principles of Biology I");
--
-- INSERT INTO
-- 	TimeSlot
-- VALUES
-- 	("2021-11-19 12:00"),
-- 	("2021-11-19 12:30"),
-- 	("2021-11-19 13:00"),
-- 	("2021-11-20 12:00");
--
-- INSERT INTO
-- 	Student (firstName, lastName, email, grade, major)
-- VALUES
-- 	(
-- 		"jon",
-- 		"sou",
-- 		"jon.sou@sjsu.edu",
-- 		2,
-- 		"computer science"
-- 	),
-- 	(
-- 		"andy",
-- 		"smith",
-- 		"andy.smith@sjsu.edu",
-- 		3,
-- 		"computer science"
-- 	),
-- 	("bob", "joe", "bob.joe@sjsu.edu", 4, "math"),
-- 	(
-- 		"alex",
-- 		"doe",
-- 		"alex.doe@sjsu.edu",
-- 		1,
-- 		"psychology"
-- 	);
--
-- INSERT INTO
-- 	Tutor (firstName, lastName, email)
-- VALUES
-- 	("tracy", "tran", "tracy.tran@sjsu.edu"),
-- 	("trunk", "troe", "trunk.troe@sjsu.edu"),
-- 	("trudy", "tuff", "trudy.tuff@sjsu.edu"),
-- 	("tom", "touve", "tom.touve@sjsu.edu"),
-- 	(
-- 		"michael",
-- 		"johnson",
-- 		"michael.johnson@sjsu.edu"
-- 	);

INSERT INTO
	Assignment (courseCode, studentID, dueDate, status, comments)
VALUES
	(
		"CS146",
		1,
		"2021-11-20",
		"incomplete",
		"quick sort"
	),
	(
		"MATH42",
		3,
		"2021-11-25",
		"incomplete",
		"factorials"
	),
	(
		"BIOL30",
		2,
		"2021-11-30",
		"complete",
		"DNA vs RNA"
	);
	/*Tutor shouldn't be able to be booked with multiple students in same time slot*/
INSERT INTO
	Session (studentID,tutorID,assignmentID,duration,startTime)
VALUES
	(1, 2, 3, 30, "2021-11-19 12:00"),
	(1, 2, 1, 30, "2021-11-19 12:30"),
	(2, 3, 1, 30, "2021-11-19 12:30"),
	(2, 2, 2, 30, "2021-11-20 12:00");

INSERT INTO
	Student_course
VALUES
	(1, "CS146"),
	(1, "BIOL10"),
	(1, "GEOL1"),
	(2, "CS146"),
	(2, "MATH42");

INSERT INTO
	Tutor_course
VALUES
	(1, "GEOL1"),
	(2, "CS146"),
	(2, "GEOL1");

INSERT INTO
	Student_language
VALUES
	(1, "english"),
	(2, "english"),
	(2, "spanish"),
	(3, "english");

INSERT INTO
	Tutor_language
VALUES
	(1, "english"),
	(1, "spanish"),
	(2, "english"),
	(3, "english"),
	(4, "english");

INSERT INTO
	Tutor_availability
VALUES
	(1, "2021-11-19 12:30", TRUE),
	(3, "2021-11-19 12:30", TRUE),
	(2, "2021-11-19 12:00", FALSE),
	(2, "2021-11-19 12:30", FALSE),
	(2, "2021-11-20 12:00", FALSE);
