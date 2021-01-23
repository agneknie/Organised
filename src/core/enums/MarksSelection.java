package core.enums;

/**
 * Enum representing Marks Tab selections, which determine
 * the displayed information.
 */
public enum MarksSelection {

    DEGREE("Degree"),
    YEAR("Year"),
    MODULE("Module"),
    ASSIGNMENT("Assignment");

    public final String VALUE;
    MarksSelection(String value){
        VALUE = value;
    }

    @Override
    public String toString() {
        return VALUE;
    }
}
