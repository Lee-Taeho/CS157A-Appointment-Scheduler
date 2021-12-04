/*
 TUTOR MANAGER DATABASE
 */
/*
 -----------------------------------------
 ADMINISTRATIVE TABLES
 */
DROP TABLE IF EXISTS Language;

CREATE TABLE Language(language VARCHAR(20) PRIMARY KEY);

DROP TABLE IF EXISTS Course;

CREATE TABLE Course(
	courseCode VARCHAR(10) PRIMARY KEY,
	name VARCHAR(50)
);

/*
 Lists all possible starting time slots the tutoring center offers
 Example: Monday 1pm, Monday 1:30pm, ...
 */
DROP TABLE IF EXISTS TimeSlot;

CREATE TABLE timeSlot(startTime DATETIME);

/* 
 -----------------------------------------
 PRIMARY TABLES
 */
DROP TABLE IF EXISTS Student;

DROP TABLE IF EXISTS Tutor;

DROP TABLE IF EXISTS Session;

DROP TABLE IF EXISTS Assignment;

CREATE TABLE Student(
	studentID INT PRIMARY KEY AUTO_INCREMENT,
	firstName VARCHAR (50),
	lastName VARCHAR (50),
	email VARCHAR(50) UNIQUE CHECK (email LIKE "%@%.%"),
	grade INT,
	major VARCHAR(20)
);

CREATE TABLE Tutor(
	tutorID INT PRIMARY KEY AUTO_INCREMENT,
	firstName VARCHAR (50),
	lastName VARCHAR (50),
	email VARCHAR(50) UNIQUE CHECK (email LIKE "%@%.%")
);

CREATE TABLE Assignment(
	assignmentID INT PRIMARY KEY AUTO_INCREMENT,
	courseCode VARCHAR(10) REFERENCES Courses(courseCode),
	studentID INT REFERENCES Student(studentID),
	dueDate DATETIME,
	status VARCHAR(20) CHECK (status IN ("complete", "incomplete")),
	comments VARCHAR(100)
);

CREATE TABLE Session(
	sessionID INT PRIMARY KEY AUTO_INCREMENT,
	studentID INT REFERENCES Student(studentID),
	tutorID INT REFERENCES Tutor(tutorID),
	assignmentID INT REFERENCES Assignment(assignmentID),
	duration INT,
	startTime DATETIME REFERENCES Availability(availabilityID)
);

/*
 -----------------------------------------
 AUXILIARY TABLES
 */
/*
 Lists all courses each student are taking and courses tutors have taken
 */
DROP TABLE IF EXISTS Student_course;

CREATE TABLE Student_course(
	studentID INT REFERENCES Student(studentID),
	courseCode VARCHAR(10) REFERENCES Course(courseCode),
	PRIMARY KEY (studentID, courseCode)
);

DROP TABLE IF EXISTS Tutor_course;

CREATE TABLE Tutor_course(
	tutorID INT REFERENCES Tutor(tutorID),
	courseCode VARCHAR(10) REFERENCES Course(courseCode),
	PRIMARY KEY (tutorID, courseCode)
);

/*
 Lists all languages each person can speak
 */
DROP TABLE IF EXISTS Student_language;

CREATE TABLE Student_language(
	studentID INT REFERENCES Student(studentID),
	language VARCHAR(20) REFERENCES Language(language),
	PRIMARY KEY (studentID, language)
);

DROP TABLE IF EXISTS Tutor_language;

CREATE TABLE Tutor_language(
	tutorID INT REFERENCES Tutor(tutorID),
	language VARCHAR(20) REFERENCES Language(language),
	PRIMARY KEY (tutorID, language)
);

/*
 Lists all availability time slots for each tutor
 */
DROP TABLE IF EXISTS Tutor_availability;

CREATE TABLE Tutor_availability(
	tutorID INT REFERENCES Tutor(tutorID),
	startTime DATETIME REFERENCES timeSlot(startTime),
	occupied BOOLEAN,
	PRIMARY KEY (tutorID, startTime)
);

/*
 Archive all old Sessions
 */
DROP TABLE IF EXISTS Archived_session;

CREATE TABLE Archived_session(
	sessionID INT PRIMARY KEY,
	studentID INT,
	tutorID INT,
	assignmentID INT,
	duration INT,
	startTime DATETIME
);