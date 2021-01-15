package core;

/**
 * Class to represent an Assignment in the application.
 */
public class Assignment {
    private int id;
    private int userId;
    private String moduleCode;
    private String fullName;
    private int percentWorth;
    private int maxScore;
    private int score;

    /**
     * Getter for assignment id
     * @return id of the assignment
     */
    public int getId() {
        return id;
    }
    /**
     * Getter for assignment user id
     * @return user id of the assignment
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Getter for assignment module code
     * @return module code of the assignment
     */
    public String getModuleCode() {
        return moduleCode;
    }

    /**
     * Getter for assignment name
     * @return full name of the assignment
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Setter for assignment name
     * @param fullName name to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Getter for percentage which the assignment contributes to
     * module overall mark.
     * @return weight as % of the assignment
     */
    public int getPercentWorth() {
        return percentWorth;
    }

    /**
     * Setter for assignment weight towards the module.
     * @param percentWorth percentage to set
     */
    public void setPercentWorth(int percentWorth) {
        if (percentWorth > 100 || percentWorth < 0)
            throw new IllegalArgumentException("Percentage has to be between 0% and 100");
        this.percentWorth = percentWorth;
    }

    /**
     * Getter for assignment maximum score
     * @return maximum score of the assignment
     */
    public int getMaxScore() {
        return maxScore;
    }

    /**
     * Setter for the maximum score of the assignment
     * @param maxScore score to set
     */
    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    /**
     * Getter for assignment score
     * @return score of the assignment
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter for the score of the assignment.
     * @param score score to set
     */
    public void setScore(int score) {
        if (score > maxScore)
            throw new IllegalArgumentException("Score can't be bigger than maximum score");
        this.score = score;
    }
}
