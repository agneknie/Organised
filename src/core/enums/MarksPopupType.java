package core.enums;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
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
