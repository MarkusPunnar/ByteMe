CREATE DATABASE byteme;

CREATE TABLE Activeinstances (
    ID int NOT NULL,
    Hostname varchar (255) NOT NULL,
    elementAmount int,
    PRIMARY KEY (ID)
);

CREATE TABLE InstanceElements (
    ID int NOT NULL,
    InstanceID int NOT NULL,
    elementContent varchar(255),
    PRIMARY KEY (ID),
    FOREIGN KEY (InstanceID) REFERENCES ActiveInstances(ID)
);

CREATE TABLE grades (
    ID int NOT NULL,
    instanceID int NOT NULL,
    elementID int NOT NULL,
    gradeScore int NOT NULL,
	  PRIMARY KEY (ID),
    FOREIGN KEY (elementID) REFERENCES InstanceElements(ID)
);

CREATE TABLE users (
    userID int NOT NULL,
    username VARCHAR(255),
    hashedPassword VARCHAR(255),
    useremail varchar(255),
    PRIMARY KEY (userID)
);