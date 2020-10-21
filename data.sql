CREATE database customer;
use customer;

CREATE USER 'customer-user'@'localhost' IDENTIFIED BY 'Q1w2e3r$';

GRANT ALL PRIVILEGES ON customer.* TO 'customer-user'@'localhost';

CREATE TABLE IF NOT EXISTS `driver`(
    `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(100),
    `status` VARCHAR(10),
    `latitude` DECIMAL(10, 8),
    `longitude` DECIMAL(11, 8),
     INDEX(`latitude`), INDEX(`longitude`)
);


CREATE TABLE IF NOT EXISTS `orders`(
    `id` VARCHAR(255) NOT NULL ,
    `customer_name` VARCHAR(100),
    `latitude` DECIMAL(10, 8),
    `longitude` DECIMAL(11, 8),
    driver_id varchar(255),
        primary key (id)
     INDEX(`latitude`), INDEX(`longitude`)
);

delimiter //
CREATE FUNCTION distance (latA double, lonA double, latB double, LonB double)
    RETURNS double DETERMINISTIC
BEGIN
    SET @RlatA = radians(latA);
    SET @RlonA = radians(lonA);
    SET @RlatB = radians(latB);
    SET @RlonB = radians(LonB);
    SET @deltaLat = @RlatA - @RlatB;
    SET @deltaLon = @RlonA - @RlonB;
    SET @d = SIN(@deltaLat/2) * SIN(@deltaLat/2) +
        COS(@RlatA) * COS(@RlatB) * SIN(@deltaLon/2)*SIN(@deltaLon/2);
    RETURN 2 * ASIN(SQRT(@d)) * 6371.01;
END//

      
  INSERT INTO `role`(`role_id`, `role_name`, `role_code`, `role_type`)
  VALUES
	('210', 'D1', '18.9000000' ,'C' ),
	('211', 'D2', '18.9900000' ,'A' );
	
	INSERT INTO user (user_id,gender,user_name,role_id) values('234322','1','aniket1','210'); 
	