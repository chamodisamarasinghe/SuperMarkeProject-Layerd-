DROP DATABASE IF EXISTS SuperMarket;
CREATE DATABASE IF NOT EXISTS SuperMarket;
SHOW DATABASES ;
USE SuperMarket;
#-------------------------------------------------

DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer(
   customerId VARCHAR(6),
   title VARCHAR(5),
   name VARCHAR(30) NOT NULL DEFAULT 'Unknown',
   address VARCHAR(30),
   city VARCHAR(30),
   province VARCHAR(30),
   postalCode VARCHAR(30),
   CONSTRAINT PRIMARY KEY (customerId)
);
SHOW TABLES ;
DESCRIBE Customer;
#-------------------------------------------------------

DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item(
   code VARCHAR(6),
   description VARCHAR(50),
   packSize VARCHAR(20),
   qtyOnHand INT DEFAULT 0,
   unitPrice DOUBLE,
   CONSTRAINT PRIMARY KEY (code)
);
SHOW TABLES ;
DESCRIBE Item;
#-------------------------------------------------------

DROP TABLE IF EXISTS `Order`;
CREATE TABLE IF NOT EXISTS `Order`(
   orderId VARCHAR(6),
   customerId VARCHAR(6),
   orderDate DATE,
   time VARCHAR(15),
   total DOUBLE,
   CONSTRAINT PRIMARY KEY (orderId),
   CONSTRAINT FOREIGN KEY (customerId) REFERENCES Customer(customerId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE `Order`;
#---------------------------------------------------------

DROP TABLE IF EXISTS `Order Detail`;
CREATE TABLE IF NOT EXISTS `Order Detail`(
   code VARCHAR(6),
   orderId VARCHAR(6),
   qty INT(11),
   unitPrice DOUBLE,
   CONSTRAINT PRIMARY KEY (code, orderId),
   CONSTRAINT FOREIGN KEY (code) REFERENCES Item(code) ON DELETE CASCADE ON UPDATE CASCADE ,
   CONSTRAINT FOREIGN KEY (orderId) REFERENCES `Order`(orderId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE `Order Detail`;
#-------------------------------------------------------------

DROP TABLE IF EXISTS Users;
CREATE TABLE IF NOT EXISTS Users(
	userName VARCHAR(50) NOT NULL,
	name VARCHAR(45) NOT NULL,
	email VARCHAR(30) NOT NULL,
	password VARCHAR(50) NOT NULL

	);
