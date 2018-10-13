CREATE DATABASE byteme;

USE byteme;

CREATE TABLE IF NOT EXISTS Users (
  UserID INT AUTO_INCREMENT NOT NULL,
  GoogleID varchar(255),
  Displayname varchar(255),
  Hashedpassword varchar(255),
  Email varchar(255),
  PRIMARY KEY (UserID)
);

CREATE TABLE IF NOT EXISTS Rooms (
    RoomID INT NOT NULL,
    Hostname INT NOT NULL,
    elementAmount INT,
    PRIMARY KEY (RoomID),
    FOREIGN KEY (Hostname) REFERENCES Users (UserID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Roomusers (
	RoomID INT,
  ConnecteduserID INT,
  FOREIGN KEY (ConnecteduserID) REFERENCES Users(UserID) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Elements (
    ElementID int NOT NULL AUTO_INCREMENT,
    RoomID int NOT NULL,
    ElementContent varchar(255) NOT NULL,
    ElementType varchar(255) NOT NULL,
    PRIMARY KEY (ElementID),
    FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Grades (
    GradeID int NOT NULL AUTO_INCREMENT,
    RoomID int NOT NULL,
    ElementID int NOT NULL,
    GradeScore int NOT NULL,
	  PRIMARY KEY (GradeID),
    FOREIGN KEY (ElementID) REFERENCES Elements(ElementID) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID) ON UPDATE CASCADE ON DELETE CASCADE
);
