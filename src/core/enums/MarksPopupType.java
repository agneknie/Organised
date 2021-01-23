package core.enums;

/**
 * Enum representing Marks Tab popup type for
 * differing action button.
 */
public enum MarksPopupType {
    EDIT("Edit"),
    ADD("Add");

    public final String VALUE;
    MarksPopupType(String value){
        VALUE = value;
    }

    @Override
    public String toString() {
        return VALUE;
    }
}
