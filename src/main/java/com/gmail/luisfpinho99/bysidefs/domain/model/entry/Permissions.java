package com.gmail.luisfpinho99.bysidefs.domain.model.entry;

import javax.persistence.Embeddable;

@Embeddable
public class Permissions {

    protected final boolean ownerRead;
    protected final boolean ownerWrite;
    protected final boolean ownerExecute;

    protected final boolean groupRead;
    protected final boolean groupWrite;
    protected final boolean groupExecute;

    protected final boolean everyoneRead;
    protected final boolean everyoneWrite;
    protected final boolean everyoneExecute;

    private static final String READ_IDENTIFIER = "r";
    private static final String WRITE_IDENTIFIER = "w";
    private static final String EXECUTE_IDENTIFIER = "x";
    private static final String NO_PERMISSION_IDENTIFIER = "-";
    
    public Permissions(boolean ownerRead, boolean ownerWrite, boolean ownerExecute, boolean groupRead, boolean groupWrite, boolean groupExecute, boolean everyoneRead, boolean everyoneWrite, boolean everyoneExecute) {
        this.ownerRead = ownerRead;
        this.ownerWrite = ownerWrite;
        this.ownerExecute = ownerExecute;

        this.groupRead = groupRead;
        this.groupWrite = groupWrite;
        this.groupExecute = groupExecute;

        this.everyoneRead = everyoneRead;
        this.everyoneWrite = everyoneWrite;
        this.everyoneExecute = everyoneExecute;
    }

    /**
     * For ORM only.
     */
    protected Permissions() {
        this.ownerRead = false;
        this.ownerWrite = false;
        this.ownerExecute = false;

        this.groupRead = false;
        this.groupWrite = false;
        this.groupExecute = false;

        this.everyoneRead = false;
        this.everyoneWrite = false;
        this.everyoneExecute = false;
    }

    @Override
    public String toString() {
        return String.format(
                "%s%s%s%s%s%s%s%s%s",
                ownerRead ? READ_IDENTIFIER : NO_PERMISSION_IDENTIFIER,
                ownerWrite ? WRITE_IDENTIFIER : NO_PERMISSION_IDENTIFIER,
                ownerExecute ? EXECUTE_IDENTIFIER : NO_PERMISSION_IDENTIFIER,
                groupRead ? READ_IDENTIFIER : NO_PERMISSION_IDENTIFIER,
                groupWrite ? WRITE_IDENTIFIER : NO_PERMISSION_IDENTIFIER,
                groupExecute ? EXECUTE_IDENTIFIER : NO_PERMISSION_IDENTIFIER,
                everyoneRead ? READ_IDENTIFIER : NO_PERMISSION_IDENTIFIER,
                everyoneWrite ? WRITE_IDENTIFIER : NO_PERMISSION_IDENTIFIER,
                everyoneExecute ? EXECUTE_IDENTIFIER : NO_PERMISSION_IDENTIFIER
        );
    }

    public static Permissions parse(String string) {
        if (string.length() != 9) {
            throw new IllegalArgumentException("Invalid permissions. Use a sequence of 9 binary digits. (ex: 111100100)");
        }

        return new Permissions(
                parseDigit(string.charAt(0)),
                parseDigit(string.charAt(1)),
                parseDigit(string.charAt(2)),
                parseDigit(string.charAt(3)),
                parseDigit(string.charAt(4)),
                parseDigit(string.charAt(5)),
                parseDigit(string.charAt(6)),
                parseDigit(string.charAt(7)),
                parseDigit(string.charAt(8))
        );
    }

    private static boolean parseDigit(char character) {
        if (character == '0') {
            return false;
        } else if (character == '1') {
            return true;
        } else {
            throw new IllegalArgumentException("Invalid permissions. Use a sequence of 9 binary digits. (ex: 111100100)");
        }
    }
}
