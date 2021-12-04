DROP TABLE IF EXISTS Tutor;

Create Table Tutor(
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    ID INT PRIMARY KEY,
    email VARCHAR(50) UNIQUE CHECK (email LIKE '% @ %.edu')
);

DROP TABLE IF EXISTS Tutor_course;

CREATE TABLE Tutor_course(
    tutorID INT REFERENCES Student(studentID),
    courseCode VARCHAR(10) REFERENCES Course(courseCode),
    PRIMARY KEY (tutorID, courseCode)
);

/*
 Lists all availability time slots for each tutor
 */
DROP TABLE IF EXISTS Tutor_availability;

CREATE TABLE Tutor_availability(
    tutorID INT REFERENCES Tutor(tutorID),
    startTime DATETIME REFERENCES timeSlot(startTime),
    PRIMARY KEY (tutorID, startTime),
    occupied BOOLEAN
);

/*
 Lists all languages each student can speak
 */
DROP TABLE IF EXISTS Tutor_language;

CREATE TABLE Tutor_language(
    tutorID INT REFERENCES Student(studentID),
    language VARCHAR(20) REFERENCES Language(language),
    PRIMARY KEY (tutorID, language)
);

/*
 Lists all possible languages
 */
DROP TABLE IF EXISTS Language;

CREATE TABLE Language(language VARCHAR(20) PRIMARY KEY);

DROP TABLE IF EXISTS Session;

CREATE TABLE Session(
    sessionID INT PRIMARY KEY,
    studentID INT REFERENCES Student(studentID),
    tutorID INT REFERENCES Tutor(tutorID),
    assignmentID INT REFERENCES Assignment(assignmentID),
    duration INT,
    startTime DATETIME REFERENCES Availability(availabilityID)
);
DROP TABLE IF EXISTS Assignment;

CREATE TABLE Assignment(
    assignmentID INT PRIMARY KEY,
    courseID INT REFERENCES Courses(courseID),
    studentID INT REFERENCES Student(studentID),
    dueDate DATETIME,
    status VARCHAR(20) CHECK (status IN ( 'complete', 'incomplete')),
    comments VARCHAR(100)
);

/*
 Lists all availability time slots for each tutor
 */
DROP TABLE IF EXISTS Tutor_availability;

CREATE TABLE Tutor_availability(
    tutorID INT REFERENCES Tutor(tutorID),
    startTime DATETIME REFERENCES timeSlot(startTime),
    PRIMARY KEY (tutorID, startTime)
);

DROP TABLE IF EXISTS Course;

CREATE TABLE Course(
    courseCode VARCHAR(10) PRIMARY KEY,
    name VARCHAR(50),
    unit INT
);