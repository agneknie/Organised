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

CREATE TABLE Period(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    userId INTEGER NOT NULL,
    associatedYear INTEGER NOT NULL,
    name TEXT NOT NULL,
    minutesLeft INTEGER NOT NULL,
    FOREIGN KEY (userId)
        REFERENCES User (id)
);

CREATE TABLE Week(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    userId INTEGER NOT NULL,
    periodId INTEGER NOT NULL,
    weekNumber INTEGER NOT NULL,
    startDate TEXT NOT NULL,
    FOREIGN KEY (userId)
        REFERENCES User (id)
);

CREATE TABLE Day(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    userId INTEGER NOT NULL,
    weekId INTEGER NOT NULL,
    date TEXT NOT NULL,
    hoursSpent INTEGER NOT NULL,
    FOREIGN KEY (userId)
        REFERENCES User (id)
);

CREATE TABLE Event(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    userId INTEGER NOT NULL,
    dayId INTEGER NOT NULL,
    moduleId INTEGER NOT NULL,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    startTime TEXT NOT NULL,
    endTime TEXT NOT NULL,
    FOREIGN KEY (userId)
        REFERENCES User (id)
);

CREATE TABLE Task(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    userId INTEGER NOT NULL,
    moduleId INTEGER NOT NULL,
    weekId INTEGER NOT NULL,
    description TEXT NOT NULL,
    status TEXT NOT NULL,
    FOREIGN KEY (userId)
        REFERENCES User (id)
);

PRAGMA foreign_keys=on;