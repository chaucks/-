# -
知识库

mysql 存储过程

SHOW PROCEDURE STATUS LIKE '%%';

SHOW CREATE PROCEDURE p;

USE mydata;

DROP PROCEDURE IF EXISTS p;

DELIMITER //

CREATE PROCEDURE p()

BEGIN

 DECLARE done INT DEFAULT FALSE;
 
 DECLARE a VARCHAR(30);
 
 DECLARE b VARCHAR(30);
 
 DECLARE c INT(5);
 
 DECLARE i INT(5) DEFAULT 0;
 
 DECLARE cur CURSOR FOR
 
	SELECT `date`, sales, fid FROM total_sales;
  
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
 
 OPEN cur;
 
 REPEAT
 
	SET i = i+1;
  
	FETCH cur INTO a, b, c;
  
	SELECT i;
  
	INSERT INTO current_sales (id, dsid, `date`, sales) VALUES(i, c, a, b);
  
 UNTIL done
 
 END REPEAT;
 
 CLOSE cur;
 
 SET done = FALSE;
 
END;
//

COMMIT;

CALL p();
