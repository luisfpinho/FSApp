package com.gmail.luisfpinho99.fsapp.domain.model.group;

import com.gmail.luisfpinho99.fsapp.Preconditions;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GroupName implements Serializable {

    @Column
    private final String groupNameValue;

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 10;

    public GroupName(String groupName) {
        Preconditions.ensureNotNull("The username cannot be null.", groupName);
        Preconditions.ensureBetween(
                String.format("The length of the username must be between %d and %d.", MIN_LENGTH, MAX_LENGTH),
                MIN_LENGTH,
                MAX_LENGTH,
                groupName.length()
        );

        this.groupNameValue = groupName;
    }

    /**
     * Only for ORM.
     */
    protected GroupName() {
        // Need to initialize final fields
        groupNameValue = null;
    }

    @Override
    public String toString() {
        return groupNameValue;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof GroupName)) {
            return false;
        }

        GroupName otherGroupName = (GroupName) other;
        return groupNameValue.equals(otherGroupName.groupNameValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupNameValue);
    }

    public static GroupName from(String name) {
        return new GroupName(name);
    }
}
