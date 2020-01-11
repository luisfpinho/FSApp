package com.gmail.luisfpinho99.bysidefs.domain.model.entry;

import com.gmail.luisfpinho99.bysidefs.Preconditions;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class EntryName implements Serializable {

    private final String entryNameValue;

    private static final boolean CASE_SENSITIVE = false;

    public EntryName(String value) {
        Preconditions.ensureNotNull("The name cannot be null.", value);
        Preconditions.ensureNotEmpty("The name of entries cannot be empty.", value);
        Preconditions.ensure(String.format("The entry name '%s' is invalid.", value),
                value.matches("[a-zA-ZÀ-ÖØ-öø-ÿ0-9_+=():&#!%$\\s]+"));

        this.entryNameValue = value;
    }

    /**
     * For ORM and creating root only.
     */
    protected EntryName() {
        // Need to initialize final fields.
        entryNameValue = "";
    }

    @Override
    public String toString() {
        return this.entryNameValue.equals("") ? String.valueOf(Directory.SEPARATOR) : this.entryNameValue;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }

        if (!(otherObject instanceof EntryName)) {
            return false;
        }

        EntryName otherEntryName = (EntryName) otherObject;
        return matches(otherEntryName.entryNameValue);
    }

    @Override
    public int hashCode() {
        if (CASE_SENSITIVE) {
            return entryNameValue.hashCode();
        } else {
            return entryNameValue.toLowerCase().hashCode();
        }
    }

    public boolean matches(String name) {
        if (CASE_SENSITIVE) {
            return this.entryNameValue.equals(name);
        } else {
            return this.entryNameValue.equalsIgnoreCase(name);
        }
    }

    public static EntryName createRootName() {
        return new EntryName();
    }

    public static EntryName from(String name) {
        return new EntryName(name);
    }
}
