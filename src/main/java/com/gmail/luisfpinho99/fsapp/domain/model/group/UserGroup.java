package com.gmail.luisfpinho99.fsapp.domain.model.group;

import com.gmail.luisfpinho99.fsapp.Preconditions;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class UserGroup implements Serializable {

    @Id
    private GroupName groupName;

    public static final GroupName ROOT_GROUP_NAME = new GroupName("root");

    public UserGroup(GroupName groupName) {
        Preconditions.ensureNotNull("The name of the group cannot be null.", groupName);
        this.groupName = groupName;
    }

    /**
     * Only for ORM.
     */
    protected UserGroup() {
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof UserGroup)) {
            return false;
        }

        UserGroup otherGroup = (UserGroup) other;
        return groupName.equals(otherGroup.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupName);
    }
}
