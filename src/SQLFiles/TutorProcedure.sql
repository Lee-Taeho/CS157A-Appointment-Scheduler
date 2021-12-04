-- As a tutor, I want to be able to set and modify my availability so that I can maintain a healthy and balanced schedule. => setting availability
-- As a tutor, I want to reject some of the appointments when I have a urgent issues
-- As a tutor, I want to blacklist some of the students that I had conflicts with.
-- As a tutor, I want to report to the tutor coordinator about the issue I had during the tutoring session.
-- As a tutor, I want to view all my appointments at once.
-- As a tutor, I want to modify my qualifications when I learn new skills

DROP PROCEDURE IF EXISTS tutor_summary; /* gives all the information about the tutor including the language, course*/
DROP PROCEDURE IF EXISTS tutor_list_appointment;
DROP PROCEDURE IF EXISTS tutor_list_session;
DROP PROCEDURE IF EXISTS session_summary;
DROP PROCEDURE IF EXISTS search_appointments;
DROP PROCEDURE IF EXISTS tutor__drop_session;
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

CREATE PROCEDURE tutor_summary(IN inTutorID INT)
BEGIN
    SELECT *
    FROM Tutor
    WHERE  inTutorID = tutorID;

    SELECT language
    FROM Tutor_language
    WHERE tutorID = inTutorID;

    SELECT courseCode
    FROM Tutor_course
    WHERE tutorID = inTutorID;

END //


CREATE PROCEDURE tutor_list_session(IN inSessionID INT)
BEGIN
    SELECT *
    FROM Session
    WHERE sessionID = inSessionID;
END //


CREATE PROCEDURE session_summary(IN inSessionID INT)
BEGIN
    SELECT * FROM Session WHERE sessionID = inSessionID;

    Select *
    FROM Assingment
    Where assingmentID in 
    (SELECT assignmentID FROM Session WHERE sessionID = inSessionID);
END //

CREATE PROCEDURE search_appointments(IN inTutorID INT, IN inStartTime DATETIME, IN inEndTime DATETIME)
BEGIN
    SELECT sessionID
    FROM (SELECT startTime, duration FROM Session WHERE tutorID = inTutorID ) AS tutorSession
    Where inStartTime <= tutorSession.startTime and DATEADD(mi, tutorSession.duration, tutorSession.startTime) <= inEndTime;

END //

CREATE PROCEDURE total_hours_worked(IN inTutorID INT, OUT timeInhours FLOAT)
BEGIN
    SELECT sum(duration) / 60.0 INTO timeInhours
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



DELIMITER ;