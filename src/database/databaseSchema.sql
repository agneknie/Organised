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
    percentWeight INTEGER NOT NULL,
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
    FOREIGN KEY (studyYear)
        REFERENCES Year (yearNumber)
);

CREATE TABLE Assignment(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    userId INTEGER NOT NULL,
    moduleCode INTEGER NOT NULL,
    fullName TEXT NOT NULL,
    percentWorth INTEGER NOT NULL,
    maxScore INTEGER,
    score INTEGER,
    FOREIGN KEY (userId)
        REFERENCES User (id),
    FOREIGN KEY (moduleCode)
        REFERENCES Module (code)
);

PRAGMA foreign_keys=on;