package core;

import database.Database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Class representing a user in the system.
 */
public class User {
    private String forename;
    private String username;
    private String passwordHash;
    private boolean keepLoggedIn;
    private int id;

    private static final int PASSWORD_MIN_LENGTH = 8;

    /**
     * Getter for user's forename
     *
     * @return users's forename
     */
    public String getForename() {
        return forename;
    }

    /**
     * Getter for user's username
     *
     * @return users's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for user's password hash
     *
     * @return users's passwordHash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Getter for user's keep logged in preference
     *
     * @return users's keepLoggedIn variable
     */
    public boolean getKeepLoggedIn() {
        return keepLoggedIn;
    }

    /**
     * Getter for user id in the database
     *
     * @return users's id
     */
    public int getId(){
        return id;
    }

    /**
     * Setter assigning the value of variable keepLoggedIn. Updates the database as well.
     *
     * @param keepLoggedIn status of the user's choice of wanting to be logged in
     */
    public void setKeepLoggedIn(boolean keepLoggedIn) {
        // Sets user variable
        this.keepLoggedIn = keepLoggedIn;

        // Updates the value in the database
        PreparedStatement pStatement = null;

        String sql = "UPDATE User SET keepLoggedIn = ? WHERE username = ?";
        try {
            // Fill the prepared statement and execute
            pStatement = Database.getConnection().prepareStatement(sql);
            pStatement.setBoolean(1, this.keepLoggedIn);
            pStatement.setString(2, this.username);
            pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close prepared statement
            if (pStatement != null) {
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Constructor which takes a username and constructs an existing user
     * from the database.
     *
     * @param username username of the user to reconstruct
     * @throws SQLException if such username does not exist in the database
     */
    public User(String username) throws SQLException {
        // Constructs SQL query
        String query = "SELECT * FROM User WHERE username = ?";

        boolean found = false;
        // Opens the database, gets the user with the specified id
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            // Prepares the statement
            pStatement = Database.getConnection().prepareStatement(query);
            pStatement.setString(1, username);

            //Executes the statement, gets the result set
            rs = pStatement.executeQuery();

            // Gets the user data in the database
            while (rs.next()) {
                this.username = username;   // Supplied already
                forename = rs.getString("forename");
                passwordHash = rs.getString("passwordHash");
                keepLoggedIn = rs.getBoolean("keepLoggedIn");
                id = rs.getInt("id");
                found = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes the prepared statement and result set
            if (pStatement != null) {
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // If such user doesn't exist, throws an exception
        if (!found) throw new SQLException("Could not find username " + username + " in the database");
    }

    /**
     * Constructor which constructs a new user with given parameters.
     * To be used whilst registering a user
     *
     * @param forename forename of the user
     * @param username username of the user
     * @param password password of the user, which is later turned into password hash
     */
    public User(String forename, String username, String password) {
        // Constructs the user
        this.forename = forename;
        this.username = username;
        passwordHash = User.generatePasswordHash(password);
        keepLoggedIn = false;
    }

    /**
     * Constructor which constructs a new user with given parameters.
     * To be used when logged in user is reconstructed on startup.
     *
     * @param forename forename of the user
     * @param username username of the user
     * @param passwordHash password hash of the user
     * @param keepLoggedIn status of variable keepLoggedIn
     * @param id id of the user in the database
     */
    public User(String forename, String username, String passwordHash, Boolean keepLoggedIn, int id){
        this.forename = forename;
        this.username = username;
        this.passwordHash = passwordHash;
        this.keepLoggedIn = keepLoggedIn;
        this.id = id;
    }

    /**
     * Checks if given username already exists in the database.
     *
     * @param username username to check for existence in the database.
     * @return boolean whether username exists or not
     */
    public static boolean usernameAvailable(String username) {
        boolean usernameAvailable = false;

        // Constructs SQL query
        String query = "SELECT * FROM User WHERE username = ?";

        // Opens the database, gets the user with the specified id
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            // Prepares the statement
            pStatement = Database.getConnection().prepareStatement(query);
            pStatement.setString(1, username);

            //Executes the statement, gets the result set
            rs = pStatement.executeQuery();

            // If there are items in the result set, the username is taken
            usernameAvailable = !rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes the prepared statement and result set
            if (pStatement != null) {
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return usernameAvailable;
    }

    /**
     * Adds user to the database.
     *
     * @param user User to add
     * @return boolean whether operation was successful
     */
    public static boolean addUser(User user) {
        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        int rowsAffected = 0;

        // Sets up the query
        String query = "INSERT INTO User VALUES(null,?,?,?,?);";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, user.getUsername());
            pStatement.setString(2, user.getForename());
            pStatement.setString(3, user.getPasswordHash());
            pStatement.setBoolean(4, user.getKeepLoggedIn());

            // Result of query is true if SQL command worked
            rowsAffected = pStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes the prepared statement and result set
            if (pStatement != null) {
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // Returns whether insertion was successful
        return rowsAffected == 1;
    }

    // Methods concerning password of the user
    /**
     * Method which takes a password as a string and checks whether it matches the stored password.
     *
     * @param inputPassword password inserted by the user (whilst logging in)
     * @return true if passwords match, false otherwise
     */
    public boolean passwordsMatch(String inputPassword) {
        String inputPasswordHash = generatePasswordHash(inputPassword);

        return inputPasswordHash.equals(this.passwordHash);
    }

    /**
     * Method which takes a password and generates a hash which will be stored in memory.
     *
     * @param password which has to be hashed
     * @return string which is a generated password hash
     */
    private static String generatePasswordHash(String password) {
        String passwordHash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            passwordHash = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return passwordHash;
    }

    /**
     * Method which checks password strength:
     * - Has to have at least PASSWORD_MIN_LENGTH letters;
     * - Has to have a number;
     * - Has to have an uppercase and a lowercase letter.
     *
     * @param password password to check for strength
     * @return boolean true if password is strong enough
     */
    public static boolean isPasswordStrong(String password) {
        // Creates patterns
        Pattern upperCase = Pattern.compile("[A-Z]");
        Pattern lowerCase = Pattern.compile("[a-z]");
        Pattern number = Pattern.compile("[0-9]");

        // Creates matchers
        Matcher hasUpperCase = upperCase.matcher(password);
        Matcher hasLowerCase = lowerCase.matcher(password);
        Matcher hasNumber = number.matcher(password);

        // Checks whether password is strong
        return hasUpperCase.find() && hasLowerCase.find() && hasNumber.find() &&
                password.length() >= PASSWORD_MIN_LENGTH;
    }

    // Methods concerning user login/logout
    /**
     * Method which signs out a user. Removes from the session
     * and sets the keepLoggedIn variable to false.
     */
    public static void signOutUser() {
        Session.getSession().setKeepLoggedIn(false);
        Session.cleanSession();
    }

    /**
     * Method which goes through the database and looks for a user, which is
     * logged in (variable keepLoggedIn is true).
     * If such user exists, returns this user, if not, returns null.
     *
     * @return User which is logged in or null
     */
    public static User findLoggedInUser() {
        User loggedInUser = null;
        // Constructs SQL query
        String query = "SELECT * FROM User WHERE keepLoggedIn = True";

        // Opens the database, gets the user with the specified id
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            // Prepares the statement
            pStatement = Database.getConnection().prepareStatement(query);

            //Executes the statement, gets the result set
            rs = pStatement.executeQuery();

            // If there are items in the result set, such user exists
            while (rs.next()) {
                String username = rs.getString("username");
                String forename = rs.getString("forename");
                String passwordHash = rs.getString("passwordHash");
                int id = rs.getInt("id");
                // keepLoggedIn is true, because this wouldn't happen if it wouldn't be
                loggedInUser = new User(forename, username, passwordHash, true, id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes the prepared statement and result set
            if (pStatement != null) {
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return loggedInUser;
    }

    /**
     * Method which returns weighted grade of the degree of the user.
     * Returns -1 if no years present.
     *
     * @return degree grade of the user
     */
    public double getDegreeGrade(){
        List <Year> years = getAllYears();
        if(years.isEmpty()) return -1;

        //Variables to save interim calculation results
        double percentAchieved = 0;
        double percentAvailable = 0;

        // Goes through years of the user and saves their data
        for(Year year : years){
            double yearMark = year.getOverallGrade()*year.getPercentWorth()/100;
            if(yearMark > 0){
                percentAchieved += yearMark;
                percentAvailable += year.getPercentWorth();
            }
        }
        // If no years with grades were present, avoids division by 0
        if(percentAvailable == 0) return -1;
        // Calculates the grade and returns it
        return percentAchieved/percentAvailable*100;
    }

    /**
     * Method which returns how much of their degree a user has completed.
     *
     * @return % of degree completed
     */
    public double getDegreePercentComplete(){
        List<Year> years = getAllYears();
        double percent = 0;

        for(Year year : years){
            if(year.getOverallGrade() != -1)
                percent += year.getPercentComplete() * year.getPercentWorth();
        }

        if(percent == 0) return 0;
        else return (double)Math.round((percent/100) * 10) / 10;
    }

    /**
     * Returns user's degree classification based on the current grade.
     * Removes decimal part from the grade without rounding it.
     * Returns "-" if grade is not available yet.
     * @return String as a classification
     */
    public String getClassification(){
        double userGrade = getDegreeGrade();
        String classification;

        if (userGrade == -1) classification = "-";
        else if(userGrade < 40) classification = "Non-Honours";
        else if(userGrade < 45) classification = "Pass";
        else if(userGrade < 50) classification = "Third (3rd)";
        else if(userGrade < 60) classification = "Lower-Second (2:2)";
        else if(userGrade < 70) classification = "Upper-Second (2:1)";
        else classification = "First (1st)";

        return classification;
    }

    // Methods concerning Years of the user
    /**
     * Method which adds a newly created year to the database.
     * Used when user creates a new year.
     *
     * @param year Year to add
     */
    public void addYear(Year year){
        // Checks if Year already exists in the database
        if(year.getId() == 0) {
            // Gets Database connection
            Connection connection = Database.getConnection();
            PreparedStatement pStatement = null;

            // Sets up the query
            String query = "INSERT INTO Year VALUES(null,?,?,?,?);";
            try {
                // Fills prepared statement and executes
                pStatement = connection.prepareStatement(query);
                pStatement.setInt(1, id);
                pStatement.setInt(2, year.getYearNumber());
                pStatement.setInt(3, year.getCredits());
                pStatement.setDouble(4, year.getPercentWorth());

                pStatement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Closes the prepared statement
                if (pStatement != null) {
                    try {
                        pStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Method which gets all the years of the user existing in the database.
     *
     * @return List of Year objects
     */
    public List<Year> getAllYears(){
        ArrayList<Year> years = new ArrayList<>();

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        ResultSet rs = null;

        // Sets up the query
        String query = "SELECT * FROM Year WHERE userId = ?;";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, id);

            //Executes the statement, gets the result set
            rs = pStatement.executeQuery();

            // If there are items in the result set, reconstructs the Years and them saves in a list
            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("userId");
                int yearNumber = rs.getInt("yearNumber");
                int credits = rs.getInt("credits");
                double percentWorth = rs.getDouble("percentWorth");

                Year currentYear = new Year(id, userId, yearNumber, credits, percentWorth);
                years.add(currentYear);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes the prepared statement and result set
            if (pStatement != null) {
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // Returns a list of Year objects
        return years;
    }

    // Methods concerning deletion of Years, Modules and Assignments
    /**
     * Method which deletes an assignment from the database.
     * Used when user deletes an assignment.
     *
     * @param assignment assignment to delete
     * @return true if deletion was successful
     */
    public boolean deleteAssignment(Assignment assignment){
        // Assignment doesn't exist in the database
        if(assignment.getId() == 0) return true;

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        int rowsAffected = 0;

        // Sets up the query
        String query = "DELETE FROM Assignment WHERE id = ?;";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, assignment.getId());

            // Result of query is true if SQL command worked
            rowsAffected = pStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes the prepared statement
            if (pStatement != null) {
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // Returns whether deletion was successful
        return rowsAffected == 1;
    }

    /**
     * Method which deletes a module from the system. Deletes all module assignments
     * as well.
     * Used when user deletes a module.
     *
     * @param module module to delete
     * @return true if deletion successful, false otherwise
     */
    public boolean deleteModule(Module module){
        // Module doesn't exist in the database
        if(module.getId() == 0) return true;

        // Deletes all assignments of the module
        int counter = 0;
        List <Assignment> assignments = module.getAllAssignments();
        for(Assignment assignment : assignments){
            if(this.deleteAssignment(assignment)) counter++;
        }

        // If assignment deletion was successful, deletes the module
        if (counter == assignments.size()){

            // Gets Database connection
            Connection connection = Database.getConnection();
            PreparedStatement pStatement = null;
            int rowsAffected = 0;

            // Sets up the query
            String query = "DELETE FROM Module WHERE id = ?;";
            try {
                // Fills prepared statement and executes
                pStatement = connection.prepareStatement(query);
                pStatement.setInt(1, module.getId());

                // Result of query is true if SQL command worked
                rowsAffected = pStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Closes the prepared statement
                if (pStatement != null) {
                    try {
                        pStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            return rowsAffected == 1;
        }
        else return false;
    }

    /**
     * Method which deletes a year from the system. Deletes all modules and all assignments
     * tied to that Year as well.
     *
     * @param year year to delete
     */
    public void deleteYear(Year year){
        // Checks if Year exists in the database
        if(year.getId() != 0) {
            // Deletes all modules of the year
            int counter = 0;
            List <Module> modules = year.getAllModules();
            for(Module module : modules){
                if(this.deleteModule(module)) counter++;
            }

            // If module deletion was successful, deletes the year
            if (counter == modules.size()){

                // Gets Database connection
                Connection connection = Database.getConnection();
                PreparedStatement pStatement = null;

                // Sets up the query
                String query = "DELETE FROM Year WHERE id = ?;";
                try {
                    // Fills prepared statement and executes
                    pStatement = connection.prepareStatement(query);
                    pStatement.setInt(1, year.getId());

                    pStatement.execute();

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    // Closes the prepared statement
                    if (pStatement != null) {
                        try {
                            pStatement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    // Methods concerning Periods, Weeks & Days
    /**
     * Method which constructs a Period instance with Weeks & Days and adds them all
     * to the database.
     *
     * @param associatedYear year associated with the period
     * @param name name of the period
     * @param numberOfWeeks number of weeks a period has
     * @param startOfPeriod start date of the period (Monday)
     * @param startWeekNumber number from which the week numeration should start
     */
    public void constructPeriod(int associatedYear, String name, int numberOfWeeks, LocalDate startOfPeriod, int startWeekNumber){
        // Adds the period to the database
        Period newPeriod = new Period(this.id, associatedYear, name);
        newPeriod.addPeriod();
        int periodId = Period.getLastId();

        // Adds all weeks of the period to the database
        LocalDate startOfWeek = startOfPeriod;
        for(int i=startWeekNumber; i<numberOfWeeks+startWeekNumber; i++){
            Week newWeek = new Week(this.id, periodId, i, startOfWeek);
            newWeek.constructWeek();

            // Updates the start date for the following week
            startOfWeek = startOfWeek.plusWeeks(1);
        }
    }

    /**
     * Method which returns all Periods of the user.
     *
     * @return List of all Periods of the User.
     */
    public List<Period> getAllPeriods(){
        ArrayList<Period> periods = new ArrayList<>();

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        ResultSet rs = null;

        // Sets up the query
        String query = "SELECT * FROM Period WHERE userId = ?;";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, id);

            //Executes the statement, gets the result set
            rs = pStatement.executeQuery();

            // If there are items in the result set, reconstructs the Periods and saves them in a list
            while (rs.next()) {
                int id = rs.getInt("id");
                int associatedYear = rs.getInt("associatedYear");
                String name = rs.getString("name");
                int minutesLeft = rs.getInt("minutesLeft");

                Period newPeriod = new Period(id, this.id, associatedYear, name, minutesLeft);
                periods.add(newPeriod);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes the prepared statement and result set
            if (pStatement != null) {
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // Returns a list of Period objects
        return periods;
    }

    /**
     * Method which deletes a Period from the database.
     * Also deletes all attached Weeks and Days.
     *
     * @param period Period to delete
     */
    public void deletePeriod(Period period){
        // Checks if Period exists in the database
        if(period.getId() != 0) {
            // Deletes all Weeks of the Period
            int counter = 0;
            List <Week> weeks = period.getAllWeeks();
            for(Week week : weeks){
                if(week.deleteWeek()) counter++;
            }

            // If week deletion was successful, deletes the period
            if (counter == weeks.size()){

                // Gets Database connection
                Connection connection = Database.getConnection();
                PreparedStatement pStatement = null;

                // Sets up the query
                String query = "DELETE FROM Period WHERE id = ?;";
                try {
                    // Fills prepared statement and executes
                    pStatement = connection.prepareStatement(query);
                    pStatement.setInt(1, period.getId());

                    pStatement.execute();

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    // Closes the prepared statement
                    if (pStatement != null) {
                        try {
                            pStatement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * Method which returns how many hours on average the user
     * spends working per day.
     *
     * Only takes in days, whose weekly hours spent are not
     * equal to 0.
     *
     * @return hours spent on average per day
     */
    public double getOverallHoursSpentDayBaseline(){
        double days = 0;
        double hours = 0;

        // Goes through all user periods
        for(Period period : this.getAllPeriods()){
            // Goes through all user weeks
            for(Week week : period.getAllWeeks()){
                if(week.getAllWeekHours()>0){
                    days += 7;      // There are 7 days in a week
                    hours += week.getAllWeekHours();
                }
            }
        }

        // Calculates the result
        if(days == 0) return 0;
        else return hours/days;
    }

    /**
     * Method which returns how many hours on average the user
     * spends working per day.
     *
     * Only takes in days, whose weekly hours spent are not
     * equal to 0.
     *
     * @return hours spent on average per day in the format of X h Y min
     */
    public String getOverallHoursSpentDay(){
        int days = 0;
        int hours = 0;

        // Goes through all user periods
        for(Period period : this.getAllPeriods()){
            // Goes through all user weeks
            for(Week week : period.getAllWeeks()){
                if(week.getAllWeekHours()>0){
                    days += 7;      // There are 7 days in a week
                    hours += week.getAllWeekHours();
                }
            }
        }
        // Calculates the result
        if (days == 0) return "0h 0min";
        else {
            String h = hours/days + "h ";
            String min = hours%days*60/days+ "min ";
            return h+min;
        }
    }

    /**
     * Method which returns how many hours on average the user
     * spends working per week.
     *
     * Only takes weeks, which have at least one work hour in them.
     *
     * @return hours spent on average per week in the format of Xh
     */
    public String getOverallHoursSpentWeek(){
        int weeks = 0;
        int hours = 0;

        // Goes through all user periods
        for(Period period : this.getAllPeriods()){
            // Goes through all user weeks
            for(Week week : period.getAllWeeks()){
                if(week.getAllWeekHours()>0){
                    weeks++;
                    hours += week.getAllWeekHours();
                }
            }
        }
        // Calculates the result
        if (weeks == 0) return "0h 0min";
        else {
            String h = hours/weeks + "h ";
            String min = hours%weeks*60/weeks+ "min ";
            return h+min;
        }
    }

    /**
     * Method which returns how many hours on average the user
     * spends working per week.
     *
     * Only takes weeks, which have at least one work hour in them.
     *
     * @return hours spent on average per week
     */
    public double getOverallHoursSpentWeekBaseline(){
        double weeks = 0;
        double hours = 0;

        // Goes through all user periods
        for(Period period : this.getAllPeriods()){
            // Goes through all user weeks
            for(Week week : period.getAllWeeks()){
                if(week.getAllWeekHours()>0){
                    weeks++;
                    hours += week.getAllWeekHours();
                }
            }
        }

        // Calculates the result
        if(weeks == 0) return 0;
        else return hours/weeks;
    }

    // Methods concerning user statistics
    /**
     * Method which returns the number of assignments which are associated with the user.
     *
     * @return number of assignments of user
     */
    public int getAssignmentNumber(){
        int assignmentNumber = 0;

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        ResultSet rs = null;

        // Sets up the query
        String query = "SELECT COUNT(userId) FROM Assignment WHERE userId = ?;";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, id);

            // Executes the statement, gets the result set
            rs = pStatement.executeQuery();
            assignmentNumber = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes the prepared statement and result set
            if (pStatement != null) {
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return assignmentNumber;
    }

    /**
     * Method which returns the number of modules which are associated with the user.
     *
     * @return number of modules of user
     */
    public int getModuleNumber(){
        int moduleNumber = 0;

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        ResultSet rs = null;

        // Sets up the query
        String query = "SELECT COUNT(userId) FROM Module WHERE userId = ?;";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, id);

            // Executes the statement, gets the result set
            rs = pStatement.executeQuery();
            moduleNumber = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes the prepared statement and result set
            if (pStatement != null) {
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return moduleNumber;
    }

    /**
     * Method which returns the number of events which are associated with the user.
     *
     * @return number of events of user
     */
    public int getEventsNumber(){
        int eventsNumber = 0;

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        ResultSet rs = null;

        // Sets up the query
        String query = "SELECT COUNT(userId) FROM Event WHERE userId = ?;";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, id);

            // Executes the statement, gets the result set
            rs = pStatement.executeQuery();
            eventsNumber = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes the prepared statement and result set
            if (pStatement != null) {
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return eventsNumber;
    }

    /**
     * Method which returns the number of tasks which are associated with the user.
     *
     * @return number of tasks of user
     */
    public int getTasksNumber(){
        int tasksNumber = 0;

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        ResultSet rs = null;

        // Sets up the query
        String query = "SELECT COUNT(userId) FROM Task WHERE userId = ?;";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, id);

            // Executes the statement, gets the result set
            rs = pStatement.executeQuery();
            tasksNumber = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes the prepared statement and result set
            if (pStatement != null) {
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return tasksNumber;
    }

    /**
     * Method which returns the number of completed tasks which are associated with the user.
     *
     * @return number of completed tasks of user
     */
    public int getCompletedTasksNumber(){
        int completedTasksNumber = 0;

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        ResultSet rs = null;

        // Sets up the query
        String query = "SELECT COUNT(userId AND status) FROM Task WHERE userId = ? AND status = 'Yes';";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, id);

            // Executes the statement, gets the result set
            rs = pStatement.executeQuery();
            completedTasksNumber = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes the prepared statement and result set
            if (pStatement != null) {
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return completedTasksNumber;
    }

    /**
     * Method which calculates total time user spent in recorded time.
     *
     * @return time as string in format __ days __x __min
     */
    public String getTimeSpentOrganised(){
        int minutes = 0;

        // Goes through all periods
        for(Period period : this.getAllPeriods()){
            // Goes through all weeks
            for(Week week : period.getAllWeeks()){
                // Adds all week times
                minutes += week.getAllWeekHours()*60;
            }
            // Adds period leftover minutes
            minutes += period.getMinutesLeft();
        }

        // Returns the time as string
        if(minutes==0) return "-";
        else{
            int days = minutes/1440;
            minutes = minutes - days*1440;
            return days + " days " +minutes/60 + "h " + minutes%60 + "min";
        }
    }

    /**
     * Method which finds the busiest period of the user based on the number
     * of events in schedule during that period.
     *
     * @return busiest period of user based on schedule in format Period Name (_ events)
     */
    public String getBusiestPeriod(){
        Period busiestPeriod = null;
        int eventNumber = 0;

        // Goes through all periods of user
        for(Period period : this.getAllPeriods()){
            int currentEventNumber = 0;

            // Goes through all weeks of period
            for(Week week : period.getAllWeeks()){
                // Goes through all days of week
                for(Day day : week.getAllDays()){
                    // Adds all events to period event count
                    currentEventNumber += day.getAllEvents().size();
                }
            }

            // Compares periods, finds the one with most events
            if(busiestPeriod==null){
                busiestPeriod = period;
                eventNumber = currentEventNumber;
            }
            else if(eventNumber<currentEventNumber){
                busiestPeriod = period;
                eventNumber = currentEventNumber;
            }
        }

        // Returns the busiest period
        if(busiestPeriod==null) return "-";
        else return "Year "+busiestPeriod.getAssociatedYear()+" "+busiestPeriod.getName()+" ("+eventNumber+" events)";
    }

    /**
     * Method which finds the busiest week of the user based on the time worked during that week.
     *
     * @return busiest week of user based on time in format Period Name: Week X (__h)
     */
    public String getBusiestWeek(){
        Week busiestWeek = null;
        Period periodOfWeek = null;

        // Goes through all user periods
        for(Period period : this.getAllPeriods()){
            // Goes through all weeks
            for(Week week : period.getAllWeeks()){
                // Chooses the week, that has the most work hours
                if(busiestWeek==null){
                    busiestWeek = week;
                    periodOfWeek = period;
                }
                else if(busiestWeek.getAllWeekHours()<week.getAllWeekHours()){
                    busiestWeek = week;
                    periodOfWeek = period;
                }
            }
        }

        // Returns the busiest week
        if (periodOfWeek == null) return "-";
        else return "Year "+periodOfWeek.getAssociatedYear()+" "+periodOfWeek.getName()+": "
                +busiestWeek.toString()+" ("+busiestWeek.getAllWeekHours()+"h)";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return keepLoggedIn == user.keepLoggedIn && id == user.id && forename.equals(user.forename) &&
                username.equals(user.username) && passwordHash.equals(user.passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(forename, username, passwordHash, keepLoggedIn, id);
    }
}


