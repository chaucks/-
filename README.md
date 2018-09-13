# -
知识库

Javascript or JQuery

传数组参数

$.ajax({

	url: '',
	
	data: {key: ['1', '2', '3']},
	
	// traditional true 支持传递数组参数 重要
	
	traditional: true,
	
	async: false,
	
	type: 'post',
	
	success: function (json) {
	
	},
	
	error: function () {
	
	}
	
});

class多选

$(".class1 .class2") 选择class1元素下class2的元素（中间有空格）

$(".class1.class2") 选择同时含有class1和class2的元素（中间没有空格）

$(".class1,.class2") 选择class1或者class2的元素（中间有逗号）

$(".class1,.class2,.class3,...")

$(".class1&.class2")选择同时含有class1和class2的元素

$(".class1,.class2,.class3,.class4") 



Linux

查杀tomcat进程

ps -ef |grep tomcat

kill -9 5144



Windows 

端口杀进程

netstat -ano |findstr 8080

tasklist|findstr 3308    // 查询哪个程序占用
 
taskkill /T /F /PID 3308    // 杀死进程

命令行分区

首先，用Windows安装盘启动电脑，到下面这个界面，按Shift+F10，就会启动具有管理员权限的ＣＭＤ：

进入CMD程序后，依次输入以下命令：

1.diskpart（启动Diskpart程序）

2.list disk （查看电脑中有哪些磁盘）

3.select disk 0（选中编号为0的磁盘）

4.clean（清除磁盘所有分区）

5.convert gpt（将磁盘转换成GPT格式）

6.list partition（查看当前磁盘分区情况）

7.create partition efi size=100（默认大小为M）

8.create partition msr size =128

9.create partition primary size =102400(此处为你想设置C盘的大小)

10.两次输入exit



Mysql 

Batch Truncate Tables

SELECT CONCAT( 'truncate table ', table_name, ';' )  FROM information_schema.tables WHERE table_name LIKE 'car_base_conf%';

存储过程

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



SHOW PROCEDURE STATUS LIKE '%%';

SHOW CREATE PROCEDURE p;

USE mydata;

DROP PROCEDURE IF EXISTS p;

DELIMITER //

CREATE PROCEDURE p()

BEGIN
 DECLARE a VARCHAR(30);
 
 DECLARE b VARCHAR(30);
 
 DECLARE c INT(5);
 
 DECLARE i INT(5) DEFAULT 0;
 
 DECLARE done INT DEFAULT TRUE;
 
 DECLARE cur CURSOR FOR
 
	SELECT `date`, sales, fid 
	
	FROM total_sales;
	
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = FALSE;
 
 OPEN cur;
 
 WHILE done
 
 DO
	SET i = i+1;
	
	FETCH cur INTO a, b, c;
	
	SELECT i;
	
	INSERT INTO current_sales (id, dsid, `date`, sales) VALUES(i, c, a, b);	
	
 END WHILE;
 
 CLOSE cur;
 
 SET done = FALSE;
 
END;

//

COMMIT;

CALL p();



DROP PROCEDURE IF EXISTS proc_distribution_sales_stat_detail;

DELIMITER //

CREATE PROCEDURE proc_distribution_sales_stat_detail(IN pkid VARCHAR(50))

BEGIN

INSERT INTO distribution_sales_stat_detail (
	PKID,
	
	ORDER_ID,
	
	ACTION_ID,
	
	DIS_ID,
	
	SALE_PRICES,
	
	`YEAR`,
	
	`MONTH`,
	
	STAT_TIME
)

SELECT
	pkid PKID, 
	
	sog.order_id ORDER_ID, 
	
	sog.action_id ACTION_ID, 
	
	so.order_user DIS_ID, 
	
	sog.sale_price SALE_PRICES, 
	
	DATE_FORMAT(so.op_time, '%Y') `YEAR`, 
	
	DATE_FORMAT(so.op_time, '%m') `MONTH`,
	
	NOW() STAT_TIME
	
FROM shop_order_goods sog

JOIN shop_order so

ON so.order_id=sog.order_id

AND sog.PAY_STATUS=3

AND DATE_FORMAT(so.op_time, '%Y-%m')=DATE_FORMAT(NOW(), '%Y-%m')

ORDER BY DIS_ID;

END;

//

COMMIT;

CALL proc_distribution_sales_stat_detail();



Ubuntu

分区大小 320g硬盘

boot 200mb

swap 4g

root 80g

usr 60g

usr/local 60g

tmp 10g

srv 10g

var 20g




Mybatis

Batch Update

<update id="updateBatch" parameterType="collection">
	
	update table_name

	<trim prefix="set" suffixOverrides=",">

	    <trim prefix="memberId=CASE" suffix="END,">

		<foreach collection="collection" index="index" item="item">

		    <if test="null != item.memberId">

			WHEN id = #{item.id}

			THEN #{item.memberId}

		    </if>

		</foreach>

	    </trim>

	</trim>

	WHERE

	<foreach collection="collection" index="index" item="item" separator="or">

	    id = #{item.id}

	</foreach>
	
</update>
