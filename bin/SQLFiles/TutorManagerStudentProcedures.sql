/*
Procedures that student would like to do
*/

DROP PROCEDURE IF EXISTS Student_update_email;
DROP PROCEDURE IF EXISTS Student_update_grade;
DROP PROCEDURE IF EXISTS Student_update_major;
DROP PROCEDURE IF EXISTS Student_add_course;
DROP PROCEDURE IF EXISTS Student_add_language;

DROP PROCEDURE IF EXISTS Student_filter_tutor_courseCode;
DROP PROCEDURE IF EXISTS Student_filter_tutor_language;
DROP PROCEDURE IF EXISTS Student_filter_tutors;
DROP PROCEDURE IF EXISTS Student_request_tutor;
DROP PROCEDURE IF EXISTS Student_view_upcoming_sessions;
DROP PROCEDURE IF EXISTS Student_register;

DELIMITER //

CREATE PROCEDURE Student_update_email (IN inStudentID INT, IN inEmail VARCHAR(50))
BEGIN
	UPDATE Student
	SET email = inEmail
	WHERE studentID = inStudentID;
END //

CREATE PROCEDURE Student_update_grade (IN inStudentID INT, IN inGrade VARCHAR(1))
BEGIN
	UPDATE Student
	SET grade = inGrade
	WHERE studentID = inStudentID;
END //

CREATE PROCEDURE Student_update_major (IN inStudentID INT, IN inMajor VARCHAR(20))
BEGIN
	UPDATE Student
	SET major = inMajor
	WHERE studentID = inStudentID;
END //

CREATE PROCEDURE Student_add_course (IN inStudentID INT, IN inCourse VARCHAR(20))
BEGIN
	INSERT INTO Student_course
	VALUES (inStudentID, inCourse);
END //

CREATE PROCEDURE Student_add_language (IN inStudentID INT, IN inLanguage VARCHAR(20))
BEGIN
	INSERT INTO Student_language
	VALUES (inStudentID, inLanguage);
END //





CREATE PROCEDURE Student_filter_tutor_courseCode (IN inCourseCode VARCHAR(10))
BEGIN
	SELECT *
	FROM Tutor
	WHERE tutorID IN (
		SELECT tutorID 
		FROM Tutor_course
		WHERE courseCode = inCourseCode
	);
END //

CREATE PROCEDURE Student_filter_tutor_language (IN inLanguage VARCHAR(20))
BEGIN
	SELECT *
	FROM Tutor
	WHERE tutorID IN (
		SELECT tutorID 
		FROM Tutor_language
		WHERE language = inLanguage
	);
END //

/*
Might delete the previous two procedures in favor of this one
*/
CREATE PROCEDURE Student_filter_tutors (IN inCourseCode VARCHAR(10), IN inLanguage VARCHAR(20))
BEGIN
	SELECT *
	FROM Tutor
	WHERE tutorID IN (
		SELECT tutorID 
		FROM Tutor_language
		WHERE courseCode = inCourseCode AND language = inLanguage
	);
END //

CREATE PROCEDURE Student_request_tutor (IN studentID INT, IN tutorID INT, IN assignmentID INT, IN startTime DATETIME)
BEGIN
	INSERT INTO Session
	VALUES (studentID, tutorID, assingmentID, startTime);
END //


CREATE PROCEDURE Student_view_upcoming_sessions (IN inStudentID INT)
BEGIN
	SELECT *
	FROM Session
	WHERE studentID = inStudentID;
END //

CREATE PROCEDURE Student_register (IN inFirstName VARCHAR(50), IN inLastName VARCHAR(50), IN inEmail VARCHAR(50), IN inGrade INT, IN inMajor VARCHAR(20))
BEGIN
	INSERT INTO Student (firstName, lastName, email, grade, major)
	VALUES (inFirstName, inLastName, inEmail, inGrade, inMajor);
END //


DELIMITER ;