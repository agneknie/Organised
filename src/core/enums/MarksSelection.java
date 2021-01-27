package core.enums;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
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

    /**
     * Returns the enum, which is after the given one
     * @return next enum
     */
    public MarksSelection next(){
        switch (this){
            case DEGREE:
                return YEAR;
            case YEAR:
                return MODULE;
            case MODULE:
                return ASSIGNMENT;
            case ASSIGNMENT:
                return DEGREE;
            default:
                return null;
        }
    }
}
