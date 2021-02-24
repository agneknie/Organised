package core;

import core.enums.Semester;
import database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Class for representing a year in the system
 */
public class Year {
    private final int id;
    private final int userId;
    private final int yearNumber;
    private int credits;
    private double percentWorth;

    /**
     * Getter for id.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for yearNumber.
     * @return yearNumber
     */
    public int getYearNumber() {
        return yearNumber;
    }

    /**
     * Getter for credits.
     * @return credits
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Setter for credits.
     * @param credits credits to set
     */
    public void setCredits(int credits) {
        if(credits < 0)
            throw new IllegalArgumentException("Credits can't be less than 0");
        this.credits = credits;
    }

    /**
     * Getter for year weight in percentage.
     * @return percentWorth
     */
    public double getPercentWorth() {
        return percentWorth;
    }

    /**
     * Setter for year weight in percent.
     * @param percentWorth percentWorth to set.
     */
    public void setPercentWorth(double percentWorth) {
        if(percentWorth < 0)
            throw new IllegalArgumentException("Percentage worth can't be less than 0");

        this.percentWorth = (double)Math.round(percentWorth * 10) / 10;
    }

    /**
     * Constructor for creating a new Year instance.
     * Used for when user is adding a new Year.
     *
     * @param yearNumber number of the year
     * @param credits how many credits does the year have
     * @param percentWorth how much does it weight toward the final degree mark
     */
    public Year (int userId, int yearNumber, int credits, double percentWorth){
        if(yearNumber < 0)
            throw new IllegalArgumentException("Year can't be less than 0");
        if(credits < 0)
            throw new IllegalArgumentException("Credits can't be less than 0");
        if(percentWorth < 0)
            throw new IllegalArgumentException("Percentage worth can't be less than 0");
        this.id = 0;    // Year is not reconstructed from db/not already in db
        this.userId = userId;
        this.yearNumber = yearNumber;
        this.credits = credits;
        this.percentWorth = (double)Math.round(percentWorth * 10) / 10;
    }

    /**
     * Constructor for a Year already existing in the database.
     * Used for accessing existing years in the database.
     *
     * @param id id of the year
     * @param userId userId of the user to whom the year belongs to
     * @param yearNumber number of the year
     * @param credits how many credits is the year worth
     * @param percentWorth how much the year weights towards final degree mark
     */
    public Year (int id, int userId, int yearNumber, int credits, double percentWorth){
        this.id = id;
        this.userId = userId;
        this.yearNumber = yearNumber;
        this.credits = credits;
        this.percentWorth = (double)Math.round(percentWorth * 10) / 10;
    }

    /**
     * Constructor for year.
     * Constructs a year instance from the database based on user id
     * and year number.
     *
     * @param userId id of the user the year belongs to
     * @param yearNumber year number of the year
     */
    public static Year yearFromUserIdAndNumber(int userId, int yearNumber){
        // Creates empty year object
        Year newYear = null;

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        ResultSet rs = null;

        // Sets up the query
        String query = "SELECT * FROM Year WHERE userId = ? AND yearNumber = ?;";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, userId);
            pStatement.setInt(2, yearNumber);

            //Executes the statement, gets the result set
            rs = pStatement.executeQuery();

            // If there are items in the result set, reconstructs the Module
            int id = rs.getInt("id");
            int credits = rs.getInt("credits");
            double percentWorth = rs.getInt("percentWorth");

            newYear = new Year(id, userId, yearNumber, credits, percentWorth);

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

        // Returns the year
        return newYear;
    }

    /**
     * Method which takes an integer, which is supposed to be the new year number
     * and checks in the database whether that yearNumber already exists for a given user.
     *
     * @param newYearNumber number to check for duplicates
     * @param user user whose years need to be checked
     * @return true if newYearNumber is unique, false otherwise
     */
    public static boolean yearNumberAvailable(int newYearNumber, User user){
        boolean yearNumberAvailable = false;

        // Constructs SQL query
        String query = "SELECT * FROM Year WHERE yearNumber = ? AND userId = ?";

        // Opens the database, gets the user with the specified id
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            // Prepares the statement
            pStatement = Database.getConnection().prepareStatement(query);
            pStatement.setInt(1, newYearNumber);
            pStatement.setInt(2, user.getId());

            //Executes the statement, gets the result set
            rs = pStatement.executeQuery();

            // If there are items in the result set, the year number is taken
            yearNumberAvailable = !rs.next();

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
        return yearNumberAvailable;
    }

    /**
     * Method which goes through the already existing years of the given user
     * and checks how many percent can the added year be worth.
     * Used when user is adding a new year.
     *
     * e.g. Year 1 is 30%, Year 2 is 50% so new Year can have only 20% max value.
     *
     * @param loggedUser user which is adding the year
     * @return how many unused % user has left in the context of years
     */
    public static double percentWorthLeft(User loggedUser){
        List<Year> years = loggedUser.getAllYears();
        double percentWorthTotal = 0;

        // Goes through the years of the user and adds them up
        for(Year year : years){
            percentWorthTotal += year.getPercentWorth();
        }

        return (double)Math.round((100-percentWorthTotal) * 10) / 10;
    }

    /**
     * Method which goes through the modules of the year and returns
     * how many credits unassigned to a module a yar has left.
     *
     * @return how many unused credits there are left
     */
    public int creditsLeft(){
        List<Module> modules = this.getAllModules();
        int creditsTotal = 0;

        // Goes through modules and adds up credits
        for(Module module : modules){
            creditsTotal += module.getCredits();
        }

        return this.credits-creditsTotal;
    }

    /**
     * Method which updates year information in the database.
     * Used when an already existing year has its information updated.
     */
    public void updateYear (){
        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;

        // Sets up the query
        String query = "UPDATE Year SET yearNumber = ?, credits = ?, percentWorth = ? " +
                "WHERE id = ?";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, yearNumber);
            pStatement.setInt(2, credits);
            pStatement.setDouble(3, percentWorth);
            pStatement.setInt(4, id);

            pStatement.execute();

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
    }

    /**
     * Method which returns the weighted overall grade of the year.
     * Returns -1 if no modules are attached to a year.
     *
     * @return overall weighted year grade
     */
    public double getOverallGrade(){
        List<Module> modules = getAllModules();
        // No modules added yet
        if (modules.isEmpty()) return -1;

        double sum = 0;
        double divisor = 0;

        for(Module module : modules){
            if(module.getOverallGrade() != -1){
                sum += module.getOverallGrade() * module.getCredits();
                divisor += module.getCredits();
            }
        }

        // If no scores yet present
        if(divisor == 0) return -1;
        else return (double)Math.round((sum/divisor) * 10) / 10;
    }

    /**
     * Method which returns how much of the year is completed.
     * @return % of the Year completed.
     */
    public double getPercentComplete(){
        List<Module> modules = getAllModules();
        double credits = 0;

        for(Module module : modules){
            if(module.getOverallGrade() != -1)
                credits += module.getCredits() * module.getPercentComplete();
        }

        if (credits == 0) return 0;
        return (double)Math.round((credits/this.credits) * 10) / 10;
    }

    /**
     * Method which returns weighted overall Spring semester grade.
     * Returns -1 if no such modules exist in this Year.
     * Assumes that ALL_YEAR modules are evenly split between Semesters.
     *
     * @return grade in %
     */
    public double getSpringGrade(){
        List<Module> modules = getAllModules();
        // No modules added yet
        if (modules.isEmpty()) return -1;

        double sum = 0;
        double divisor = 0;

        for(Module module : modules){
            if(module.getOverallGrade() != -1){
                if(module.getSemester() == Semester.ALL_YEAR){
                    sum += module.getOverallGrade() * ((double) module.getCredits()/2);
                    divisor += (double) module.getCredits()/2;
                }
                if(module.getSemester() == Semester.SPRING){
                    sum += module.getOverallGrade() * module.getCredits();
                    divisor += module.getCredits();
                }
            }
        }

        // If no scores yet present
        if(divisor == 0) return -1;
        else return (double)Math.round((sum/divisor) * 10) / 10;
    }

    /**
     * Method which returns weighted overall Autumn semester grade
     * Returns -1 if no such modules exist in this Year.
     * Assumes that ALL_YEAR modules are evenly split between Semesters.
     *
     * @return grade in %
     */
    public double getAutumnGrade(){
        List<Module> modules = getAllModules();
        // No modules added yet
        if (modules.isEmpty()) return -1;

        double sum = 0;
        double divisor = 0;

        for(Module module : modules){
            if(module.getOverallGrade() != -1){
                if(module.getSemester() == Semester.ALL_YEAR){
                    sum += module.getOverallGrade() * ((double) module.getCredits()/2);
                    divisor += (double) module.getCredits()/2;
                }
                if(module.getSemester() == Semester.AUTUMN){
                    sum += module.getOverallGrade() * module.getCredits();
                    divisor += module.getCredits();
                }
            }
        }

        // If no scores yet present
        if(divisor == 0) return -1;
        else return (double)Math.round((sum/divisor) * 10) / 10;
    }

    // Methods concerning Modules of this year
    /**
     * Method which adds a newly created module to the database.
     * Used when user creates a new module.
     *
     * @param module Module to add
     */
    public void addModule(Module module) {
        // Checks whether Module already exists in the database
        if (module.getId() == 0) {
            // Gets Database connection
            Connection connection = Database.getConnection();
            PreparedStatement pStatement = null;

            // Sets up the query
            String query = "INSERT INTO Module VALUES(null,?,?,?,?,?,?,?);";
            try {
                // Fills prepared statement and executes
                pStatement = connection.prepareStatement(query);
                pStatement.setInt(1, userId);
                pStatement.setString(2, module.getCode());
                pStatement.setString(3, module.getFullName());
                pStatement.setInt(4, module.getCredits());
                pStatement.setString(5, module.getSemester().toString());
                pStatement.setInt(6, module.getStudyYear());
                pStatement.setString(7, module.getColourAsString());

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
     * Method which gets all modules of belonging to the year in the database.
     *
     * @return List od Module objects
     */
    public List<Module> getAllModules(){
        ArrayList<Module> modules = new ArrayList<>();

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        ResultSet rs = null;

        // Sets up the query
        String query = "SELECT * FROM Module WHERE userId = ? AND studyYear = ?;";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, userId);
            pStatement.setInt(2, yearNumber);

            //Executes the statement, gets the result set
            rs = pStatement.executeQuery();

            // If there are items in the result set, reconstructs the Modules and them saves in a list
            while (rs.next()) {
                int id = rs.getInt("id");
                String code = rs.getString("code");
                String fullName = rs.getString("fullName");
                int credits = rs.getInt("credits");
                String semester = rs.getString("semester");
                String colour = rs.getString("colour");

                Module currentModule = new Module(id, userId, code, fullName, credits,
                        semester, yearNumber, colour);
                modules.add(currentModule);
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
        // Returns a list of Module objects
        return modules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Year year = (Year) o;
        return id == year.id && userId == year.userId && yearNumber == year.yearNumber &&
                credits == year.credits && percentWorth == year.percentWorth;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, yearNumber, credits, percentWorth);
    }

    @Override
    public String toString(){
        return "Year " + this.yearNumber;
    }
}
