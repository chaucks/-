# 知识库


## IDEA

#### Tomcat 控制台乱码

idea.exe.vmoptions

idea64.exe.vmoptions

-Dfile.encoding=UTF-8




## Java

#### 成员方法也是共享的，为什么就不会出现问题呢？

先回答你这个问题：
java 里，每个线程都有自己独享的空间，也就是栈内存。线程在调用方法的时候，会创建一个栈帧。也就是说调用一个方法的时候，也就是一个栈帧的入栈过程，该方法执行完毕，栈帧也就出栈了。
换句话讲，成员方法对于每个线程事实上是私有的，而不是你表面看上去的那样是 "共享" 的。

#### 那么为什么成员变量会出问题呢？

如你所知道的，每个新建对象都存放在堆中，每个持有该对象引用的线程，都可以访问到它(只要你有那个权限)。
这也就是说，成员变量对于每个线程，事实上是共享的。

#### lambda

Comparator.comparing(j -> j.getString("USERID"));




## Vertx

#### common-logging slf4j

// common-logging -> log4j2
	
System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.Log4j2LogDelegateFactory");

// common-logging -> log4j

System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.Log4jLogDelegateFactory");

// slf4j -> logback

System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory");




## Spring

#### 定时任务 

@Scheduled(cron = "0 0 * * * *")

1.方法不能有参数

2.方法不能有返回值

3.类（class）中不能包含其他任何带注解的方法




## Apollo MQ

#### Broker command

add ${APOLLO_HOME}/bin to env path

apollo create broker

安装的broker目录执行：

"D:\apache-apollo-1.7.1-broker\broker\bin\apollo-broker" run

"D:\apache-apollo-1.7.1-broker\broker\bin\apollo-broker-service" install

"D:\apache-apollo-1.7.1-broker\broker\bin\apollo-broker-service" start




## Nginx

#### Nginx指定配置文件启动

start nginx.exe -c conf/default.conf

#### Nginx重新加载配置文件

nginx.exe -s reload




## MongoDB

#### 安装服务

mongod --config D:\mongodb\mongo.config --install --serviceName "MongoDB"




## Linux

#### 查杀tomcat进程

ps -ef |grep tomcat

kill -9 5144




## Windows 

#### 端口杀进程

netstat -ano |findstr 8080

// 查询哪个程序占用

tasklist|findstr 3308
 
// 杀死进程

taskkill /T /F /PID 3308

#### 命令行分区

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




## Maven

#### Maven <parent>

parent依赖 子pom找不到父pom的jar 添加relativePath标签 意思是从子pom的工程父级目录查找父pom文件

reimport jar 如果还有问题试着在报错的文件重新import class

	<parent>
	
        <groupId>xxx.xxx</groupId>
	
        <artifactId>xxx</artifactId>
        
	<version>x.x.x</version>
        
	<relativePath/>
	
	</parent>




## EcmaScript JavaScript or JQuery

#### 获取图片的真实宽度

Image.naturalWidth

#### 传数组参数

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

#### class多选

$(".class1 .class2") 选择class1元素下class2的元素（中间有空格）

$(".class1.class2") 选择同时含有class1和class2的元素（中间没有空格）

$(".class1,.class2") 选择class1或者class2的元素（中间有逗号）

$(".class1,.class2,.class3,...")

$(".class1&.class2")选择同时含有class1和class2的元素

$(".class1,.class2,.class3,.class4") 




## Mysql

#### 事务死锁处理

-- 检索正在运行的事务

SELECT * FROM information_schema.INNODB_TRX;

-- 找到 trx_state=LOCK_WAIT的事务行

-- 执行 KILL trx_mysql_thread_id

-- 如：trx_mysql_thread_id=10468 执行 KILL 10468

KILL 10468;

-- 查看所有进程列表

SHOW PROCESSLIST;

#### 外键约束怎么清空表

1.SET FOREIGN_KEY_CHECKS=0;

2.truncate TABLE xxx;

3.SET FOREIGN_KEY_CHECKS=1;

#### 随机查询

SELECT t1.* FROM `table_name` t1, ( SELECT ROUND( RAND() * (MAX(id) - MIN(id)) + MIN(id) ) id FROM `table_name` ) t2
WHERE t1.id >= t2.id ORDER BY t1.id LIMIT 1;

#### Batch Truncate Tables

SELECT CONCAT( 'truncate table ', table_name, ';' )  FROM information_schema.tables WHERE table_name LIKE 'prefix%';

#### 查询表名

SELECT table_name FROM information_schema.tables WHERE table_schema = 'schema_name' AND table_name LIKE '%%';




## Mybatis

#### Batch Update

<insert id="insertSelective">
	
	insert into ${table}
	<trim prefix="(" suffix=")" suffixOverrides=",">
	    <foreach collection="map" index="key" item="value">
		<if test="null != key and null != value">
		    ${key},
		</if>
	    </foreach>
	</trim>
	<trim prefix="values (" suffix=")" suffixOverrides=",">
	    <foreach collection="map" index="key" item="value">
		<if test="null != key and null != value">
		    #{value},
		</if>
	    </foreach>
	</trim>
	
</insert>

<update id="updateSelective">
	
        update ${table}
        <trim prefix="SET " suffixOverrides=",">
            <foreach collection="map" index="key" item="value">
                <if test="null != key and null != value">
                    ${key} = #{value},
                </if>
            </foreach>
        </trim>
        WHERE ID = #{ID}
	
</update>

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





## Ubuntu

#### 分区大小 320g硬盘

boot 200mb

swap 4g

root 80g

usr 60g

usr/local 60g

tmp 10g

srv 10g

var 20g




#### 存储过程

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


