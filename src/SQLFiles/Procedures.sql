/*
Stored Procedures
*/

DROP PROCEDURE IF EXISTS Archive_session;

DELIMITER //

CREATE PROCEDURE Archive_session (IN deleteAllBeforeThisDate DATETIME)
BEGIN

	INSERT INTO Archived_session
	(SELECT * FROM Session WHERE startTime < deleteAllBeforeThisDate);

	DELETE FROM Session 
	WHERE startTime < deleteAllBeforeThisDate;

END //

DELIMITER ;

/*
-----------------------------------------
Student Procedures
*/

DROP PROCEDURE IF EXISTS Student_summary;
DROP PROCEDURE IF EXISTS Student_get_ID;
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

CREATE PROCEDURE Student_summary(IN inStudentID INT)
BEGIN
    SELECT Student.studentID, firstName, lastName, email, grade, major, language, courseCode
    FROM Student
    LEFT JOIN Student_language
    ON Student.studentID = Student_language.studentID
    LEFT JOIN Student_course
    ON Student.studentID = Student_course.studentID
    WHERE Student.studentID = inStudentID;
END //

CREATE PROCEDURE Student_get_ID(IN inStudentEmail VARCHAR(50))
BEGIN
    SELECT Student.studentID
    FROM Student
    WHERE inStudentEmail = email;
END //

CREATE PROCEDURE Student_update_email (IN inStudentID INT, IN inEmail VARCHAR(50))
BEGIN
	UPDATE Student
	SET email = inEmail
	WHERE studentID = inStudentID;
END //

CREATE PROCEDURE Student_update_grade (IN inStudentID INT, IN inGrade INT)
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

CREATE PROCEDURE Student_add_course (IN inStudentID INT, IN inCourse VARCHAR(10))
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
	SELECT Tutor.tutorID, firstName, lastName, email, courseCode, language
	FROM Tutor
	LEFT JOIN Tutor_language
    ON Tutor.tutorID = Tutor_language.tutorID
	LEFT JOIN Tutor_course
    ON Tutor.tutorID = Tutor_course.tutorID
	WHERE Tutor.tutorID IN (
	
		(SELECT tutorID
		FROM Tutor_course
		WHERE courseCode = inCourseCode)
		
		UNION
		
		(SELECT tutorID 
		FROM Tutor_language
		WHERE language = inLanguage));
	
	
END //

CREATE PROCEDURE Student_request_tutor (IN inStudentID INT, IN inTutorID INT, IN inAssignmentID INT, IN inDuration INT, IN inStartTime DATETIME)
BEGIN
	INSERT INTO Session (studentID, tutorID, assignmentID, duration, startTime)
	VALUES (inStudentID, inTutorID, inAssignmentID, inDuration, inStartTime);
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

/*
-----------------------------------------
Tutor Procedures
*/

-- As a tutor, I want to be able to set and modify my availability so that I can maintain a healthy and balanced schedule. => setting availability
-- As a tutor, I want to reject some of the appointments when I have a urgent issues
-- As a tutor, I want to blacklist some of the students that I had conflicts with.
-- As a tutor, I want to report to the tutor coordinator about the issue I had during the tutoring session.
-- As a tutor, I want to view all my appointments at once.
-- As a tutor, I want to modify my qualifications when I learn new skills

DROP PROCEDURE IF EXISTS tutor_info; /* gives all the information about the tutor including the language, course*/
DROP PROCEDURE IF EXISTS tutor_info_languages;
DROP PROCEDURE IF EXISTS tutor_info_courses;
DROP PROCEDURE IF EXISTS tutor_list_appointment_summary;
DROP PROCEDURE IF EXISTS tutor_get_session;
DROP PROCEDURE IF EXISTS session_assignment;
DROP PROCEDURE IF EXISTS search_appointments;
DROP PROCEDURE IF EXISTS total_hours_worked;
DROP PROCEDURE IF EXISTS tutor_add_course;

DROP PROCEDURE IF EXISTS tutor_add_language;
DROP PROCEDURE IF EXISTS tutor_add_courses;
DROP PROCEDURE IF EXISTS tutor_delete_course;
DROP PROCEDURE IF EXISTS tutor_update_name;
DROP PROCEDURE IF EXISTS tutor_drop_session;


-- a tutor might need a list of students they are taking care of
-- a tutor will need a list of time they are occupied just by their time
-- a tutor might want to cancel the appointment because they have some urgent issue.
-- a tutor might want to see the detail of the assignment they are going to help
-- a tutor might want to modify their profile


DELIMITER //

CREATE PROCEDURE tutor_info(IN inTutorID INT)
BEGIN
    SELECT *
    FROM Tutor
    WHERE  inTutorID = tutorID;
END //

CREATE PROCEDURE tutor_info_languages(IN inTutorID INT)
BEGIN
    SELECT language
    FROM Tutor_language
    WHERE tutorID = inTutorID;
END //

CREATE PROCEDURE tutor_info_courses(IN inTutorID INT)
BEGIN
    SELECT courseCode
    FROM Tutor_course
    WHERE tutorID = inTutorID;
END //

CREATE PROCEDURE tutor_list_appointment_summary(In inTutorID INT)
BEGIN
    Select Tutor.firstName as tfirst, Tutor.lastName as tlast, Student.firstName as sfirst, Student.lastName as slast, sessionID, courseCode, startTime, duration
    From Tutor
    Join
    (SELECT tutorID, Session.studentID ,sessionID, courseCode, startTime, duration
    FROM Session Join Assignment using(assignmentID)
    Where Session.tutorID = inTutorID) as sessionSummary using(tutorID)
    Join
    Student using (studentID);
END //


CREATE PROCEDURE tutor_get_session(IN inSessionID INT)
BEGIN
    SELECT *
    FROM Session
    WHERE sessionID = inSessionID;
END //


CREATE PROCEDURE session_assignment(IN inSessionID INT)
BEGIN
--     SELECT * FROM Session WHERE sessionID = inSessionID;

    Select *
    FROM Assingment
    Where assingmentID in
    (SELECT assignmentID FROM Session WHERE sessionID = inSessionID);
END //

CREATE PROCEDURE search_appointments(IN inTutorID INT, IN inStartTime DATETIME, IN inEndTime DATETIME)
BEGIN
    SELECT sessionID, startTime, duration
    FROM (SELECT sessionID, startTime, duration FROM Session WHERE tutorID = inTutorID ) AS tutorSession
    Where inStartTime <= tutorSession.startTime and DATE_ADD(tutorSession.startTime, INTERVAL tutorSession.duration MINUTE) <= inEndTime;

END //

CREATE PROCEDURE total_hours_worked(IN inTutorID INT)
BEGIN
    SELECT sum(duration) / 60.0 as total
    FROM Session
    WHERE tutorID = inTutorID;
END //







CREATE PROCEDURE tutor_add_language(IN inTutorID INT, IN inLanguage VARCHAR(20))
BEGIN
   INSERT INTO Tutor_language
   VALUES (inTutorID, inLanguage);
END //


CREATE PROCEDURE tutor_add_course(IN inTutorID INT, IN inCourseCode INT)
BEGIN
   INSERT INTO Tutor_course
   VALUES (inTutorID, inCourseCode);
END //


CREATE PROCEDURE tutor_delete_course(IN inTutorID INT, IN inCourseCode INT)
BEGIN
   DELETE FROM Tutor_course
   WHERE tutorID = inTutorID and courseCode = inCourseCode;
END //


CREATE PROCEDURE tutor_update_name(IN inTutorID INT, IN inFirstName VARCHAR(50), IN inLastName VARCHAR(50))
BEGIN
   UPDATE Tutor
   SET firstName = inFirstName, lastName =inLastName
   WHERE tutorID = inTutorID;
END //


CREATE PROCEDURE tutor_drop_session(IN inSessionID INT)
BEGIN
   DELETE FROM Session
   WHERE sessionID = inSessionID;
END //


/*
-----------------------------------------
Tutor Manager Procedures
*/

DROP PROCEDURE IF EXISTS TutorManager_update_schedule;
DROP PROCEDURE IF EXISTS TutorManager_view_schedule;
DROP PROCEDURE IF EXISTS TutorManager_update_Status;
DROP PROCEDURE IF EXISTS TutorManager_add_courses;
DROP PROCEDURE IF EXISTS TutorManager_num_tutors_of_courseCode;


CREATE PROCEDURE TutorManager_update_schedule (IN inTutorID INT, IN inCourse VARCHAR(20))
BEGIN
UPDATE Tutor
    SET course = inCourse
    WHERE tutorID = inTutorID;
END //

CREATE PROCEDURE TutorManager_view_schedule (IN inStudentID INT, IN inTutorID INT)
BEGIN
SELECT *
    FROM Session
    WHERE studentID = inStudentID AND tutorID= inTutorID;
END //

CREATE PROCEDURE TutorManager_update_Status (IN inAssignmentID INT, IN inStatus VARCHAR(20))
BEGIN
UPDATE Assignment
    SET status = inStatus
    WHERE AssignmentID = inAssignmentID;
END //

CREATE PROCEDURE TutorManager_add_courses (IN inTutorID INT, IN inCourseCode VARCHAR(10))
BEGIN
INSERT INTO Tutor_course
    VALUES (inTutorID, inCourseCode);

END //

/*
Count number of tutors that have inCourseCode; Group by Tutors Having the same courses
*/

CREATE PROCEDURE TutorManager_num_tutors_of_courseCode (IN inCourseCode VARCHAR(10))
BEGIN
	SELECT courseCode, COUNT(*) AS num
	FROM Tutor_Course
    WHERE Tutor_course.courseCode = inCourseCode
	GROUP BY courseCode;
END //

DELIMITER ;
