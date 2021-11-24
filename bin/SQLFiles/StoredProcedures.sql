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