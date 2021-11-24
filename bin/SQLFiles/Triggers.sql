DELIMITER //

/*
Rounds values inserted into timeSlot to 30 mins 
*/
CREATE TRIGGER RoundTimeSlotTrigger
BEFORE INSERT ON timeSlot
FOR EACH ROW
BEGIN 
    INSERT INTO timeSlot VALUES 
	(DATE_FORMAT(DATE_ADD(NEW.startTime, INTERVAL 30 MINUTE),'%Y-%m-%d %H:00:00'));
END //

/*
When insert into sessions, update Tutor Availability to occupied=true
*/
CREATE TRIGGER InsertSession
AFTER INSERT ON session
FOR EACH ROW
BEGIN 
    UPDATE Tutor_availability
    SET occupied=True
    WHERE tutorID=NEW.tutorID AND startTime=NEW.startTime;
END //

DELIMITER ;
