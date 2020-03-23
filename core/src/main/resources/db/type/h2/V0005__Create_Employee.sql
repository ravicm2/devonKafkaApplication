CREATE TABLE Employee
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   modificationCounter INTEGER NOT NULL,
   name VARCHAR (255),
   age INTEGER NOT NULL,
   location VARCHAR (255),
   validEmployee BOOLEAN,
   PRIMARY KEY (ID)
);