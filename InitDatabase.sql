CREATE DATABASE IF NOT EXISTS IStore;

USE IStore;

DROP TABLE IF EXISTS WORKING;
DROP TABLE IF EXISTS Inventories;
DROP TABLE IF EXISTS Items;
DROP TABLE IF EXISTS Stores;
DROP TABLE IF EXISTS Employees;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Roles;
DROP TABLE IF EXISTS WhitelistedEmail;


CREATE TABLE WhitelistedEmail(
    Email VARCHAR(50) PRIMARY KEY
);

CREATE TABLE Roles(
    IdRole INT AUTO_INCREMENT,
    Name VARCHAR(25) UNIQUE,
    PRIMARY KEY (IdRole)
);

CREATE TABLE Users(
    IdUser INT AUTO_INCREMENT,
    Email VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(255) UNIQUE NOT NULL,
    Username VARCHAR(50) UNIQUE NOT NULL,
    IdRole INT NOT NULL,
    PRIMARY KEY (IdUser),
    FOREIGN KEY (IdRole) REFERENCES Roles(IdRole)
);

CREATE TABLE Employees(
    IdEmployee INT AUTO_INCREMENT,
    IdUser INT,
    PRIMARY KEY (IdEmployee),
    FOREIGN KEY (IdUser) REFERENCES Users(IdUser)
);

CREATE TABLE Stores(
    IdStore INT AUTO_INCREMENT,
    Name VARCHAR(50),
    PRIMARY KEY (IdStore)
);

CREATE TABLE Items(
    IdItem INT AUTO_INCREMENT,
    Name VARCHAR(50) UNIQUE NOT NULL,
    Price DOUBLE NOT NULL,
    PRIMARY KEY (IdItem),
    CONSTRAINT check_price CHECK ( Price>=0 )
);

CREATE TABLE Inventories(
    IdStore int,
    IdItem int,
    Amount int default 1,
    PRIMARY KEY (IdStore,IdItem),
    FOREIGN KEY (IdStore) REFERENCES Stores(IdStore),
    FOREIGN KEY (IdItem) REFERENCES Items(IdItem),
    CONSTRAINT check_amount CHECK ( Amount>=0 )
);

CREATE TABLE WORKING(
    IdStore int,
    IdUser int,
    PRIMARY KEY (IdStore,IdUser),
    FOREIGN KEY (IdStore) REFERENCES Stores(IdStore),
    FOREIGN KEY (IdUser) REFERENCES Users(IdUser)
);

INSERT INTO Items (Name, Price) VALUES ('Laptop', 999.99);
INSERT INTO Items (Name, Price) VALUES ('Smartphone', 699.99);
INSERT INTO Items (Name, Price) VALUES ('Headphones', 149.99);
INSERT INTO Items (Name, Price) VALUES ('Keyboard', 49.99);
INSERT INTO Items (Name, Price) VALUES ('Mouse', 29.99);