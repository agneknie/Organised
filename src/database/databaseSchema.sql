CREATE TABLE User(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    forename TEXT NOT NULL,
    passwordHash TEXT NOT NULL,
    keepLoggedIn BOOLEAN NOT NULL
);

CREATE TABLE Year(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    userId INTEGER NOT NULL,
    yearNumber INTEGER NOT NULL,
    credits INTEGER NOT NULL,
    percentWorth REAL NOT NULL,
    FOREIGN KEY (userId)
        REFERENCES User (id)
);

CREATE TABLE Module(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    userId INTEGER NOT NULL,
    code TEXT NOT NULL,
    fullName TEXT NOT NULL,
    credits INTEGER NOT NULL,
    semester TEXT NOT NULL,
    studyYear INTEGER NOT NULL,
    colour TEXT NOT NULL,
    FOREIGN KEY (userId)
        REFERENCES User (id)
);

CREATE TABLE Assignment(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    userId INTEGER NOT NULL,
    moduleCode INTEGER NOT NULL,
    fullName TEXT NOT NULL,
    percentWorth REAL NOT NULL,
    maxScore REAL,
    score REAL,
    FOREIGN KEY (userId)
        REFERENCES User (id)
);

PRAGMA foreign_keys=on;