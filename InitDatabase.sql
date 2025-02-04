CREATE DATABASE IF NOT EXISTS IStore;

USE IStore;

DROP TABLE IF EXISTS WORKING;
DROP TABLE IF EXISTS Inventories;
DROP TABLE IF EXISTS Items;
DROP TABLE IF EXISTS Stores;
DROP TABLE IF EXISTS Employees;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS WhitelistedEmail;


CREATE TABLE WhitelistedEmail(
    Email VARCHAR(50) PRIMARY KEY
);

CREATE TABLE Users(
    IdUser INT AUTO_INCREMENT,
    Email VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Username VARCHAR(50) UNIQUE NOT NULL,
    IdRole INT DEFAULT 3,
    PRIMARY KEY (IdUser),
    CONSTRAINT check_role CHECK ( 1<=IdRole AND IdRole<=3 )
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
    FOREIGN KEY (IdStore) REFERENCES Stores(IdStore) ON DELETE CASCADE ,
    FOREIGN KEY (IdItem) REFERENCES Items(IdItem) ON DELETE CASCADE ,
    CONSTRAINT check_amount CHECK ( Amount>=0 )
);

CREATE TABLE WORKING(
    IdStore int,
    IdUser int,
    PRIMARY KEY (IdStore,IdUser),
    FOREIGN KEY (IdStore) REFERENCES Stores(IdStore) ON DELETE CASCADE ,
    FOREIGN KEY (IdUser) REFERENCES Users(IdUser) ON DELETE CASCADE
);

INSERT INTO Items (Name, Price)
VALUES  ('Laptop', 999.99),
        ('Smartphone', 699.99),
        ('Headphones', 149.99),
        ('Keyboard', 49.99),
        ('Mouse', 29.99);

INSERT INTO WhitelistedEmail (Email)
VALUES ("candicedambrin@gmail.com"),
       ("clementbertin@gmail.com");

INSERT INTO Stores (Name)
VALUES ("Monoprix"),
       ("Auchan");

INSERT INTO Users (Email, Password, Username, IdRole)
VALUE ("admin@istore.com","$2a$10$wUItoNaEW9JbZV9JvIJFQ.qaTxUSQ/iikGnTFbqji5kGYe2eOym/i","Admin",1);


INSERT INTO Inventories(IdStore, IdItem, Amount)
VALUES (1,1,5),
       (1,3,1),
       (2,3,4);