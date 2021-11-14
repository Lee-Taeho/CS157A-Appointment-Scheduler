INSERT INTO Language VALUES 
	("English"),
	("Spanish"),
	("German");
	
INSERT INTO Course VALUES 
	("CS146", "data structures"),
	("GEOL1", "intro to geology"),
	("MATH161A", "probability and statistics");

INSERT INTO TimeSlot VALUES
	("2021-11-19 12:00"),
	("2021-11-19 12:30"),
	("2021-11-19 13:00"),
	("2021-11-20 12:00");
	
INSERT INTO Student (firstName, lastName, email, grade, major)
VALUES
	("jon", "sou", "jon.sou@sjsu.edu", 2, "computer science"),
	("bob", "joe", "bob.joe@sjsu.edu", 4, "math");
	
INSERT INTO Tutor (firstName, lastName, email)
VALUES
	("tracy", "tran", "tracy.tran@sjsu.edu"),
	("trunk", "troe", "trunk.troe@sjsu.edu");
	
INSERT INTO Assignment (courseCode, studentID, dueDate, status, comments) 
VALUES 
	("CS146", 1, "2021-11-20", "incomplete", "depth breadth search");
	
INSERT INTO Session (studentID, tutorID, assignmentID, duration, startTime) 
VALUES 
	(1, 2, 1, 30, "2021-11-19 13:30:00"),
	(2, 2, 1, 30, "2021-11-19");
	
INSERT INTO Student_course VALUES
	(1, "CS146"),
	(1, "GEOL1"),
	(2, "MATH161A");
	
INSERT INTO Tutor_course VALUES
	(1, "GEOL1"),
	(2, "CS146"),
	(2, "GEOL1");
	
INSERT INTO Student_language VALUES
	(1, "english"),
	(2, "spanish");
	
INSERT INTO Tutor_language VALUES 
	(1, "english"),
	(1, "spanish"),
	(2, "english");
	
INSERT INTO Tutor_availability VALUES
	(1, "2021-11-19 12:30", TRUE),
	(1, "2021-11-19 13:00", FALSE),
	(1, "2021-11-20 12:00", FALSE),
	(2, "2021-11-20 12:00", FALSE);
	