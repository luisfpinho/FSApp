package com.gmail.luisfpinho99.fsapp.domain.model.user;

import com.gmail.luisfpinho99.fsapp.Preconditions;
import com.gmail.luisfpinho99.fsapp.domain.model.group.UserGroup;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class User implements Serializable {

    @EmbeddedId
    private Username username;
    private Password password;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserGroup group;

    public static final Username ROOT_USERNAME = new Username("root");

    public User(Username username, Password password, UserGroup group) {
        Preconditions.ensureNotNull(username, password, group);

        this.username = username;
        this.password = password;
        this.group = group;
    }

    protected User() {
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof User)) {
            return false;
        }

        User otherUser = (User) other;
        return username.equals(otherUser.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public Username getUsername() {
        return username;
    }

    public UserGroup getGroup() {
        return group;
    }

    public boolean isRoot() {
        return this.username.equals(ROOT_USERNAME);
    }

    public boolean authenticate(String password) {
        return this.password.authenticate(password);
    }
}
