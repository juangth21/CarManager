CREATE DATABASE carmanager;

USE carmanager;

CREATE TABLE User (
    username VARCHAR(100) PRIMARY KEY,
    name VARCHAR(100),
    password VARCHAR(256),
    email VARCHAR(100)
);

CREATE TABLE garage (
    id INT(10) unsigned AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(100),
    capacity INT(20),
    lengthy FLOAT(8) unsigned,
    width FLOAT(4) unsigned,
    height FLOAT(3) unsigned,
    user_username VARCHAR(100),
    FOREIGN KEY (user_username) REFERENCES User(username)
);

CREATE TABLE cars (
    id INT(10) unsigned AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(100),
    tuition INT(5) unsigned,
    weight INT(200) unsigned
);

CREATE TABLE stores (
    garageId INT(10) unsigned,
    carsId INT(10) unsigned,
    PRIMARY KEY (garageId, carsId),
    FOREIGN KEY (garageId) REFERENCES garage(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (carsId) REFERENCES cars(id) ON DELETE CASCADE ON UPDATE CASCADE
);