package com.gmail.luisfpinho99.bysidefs.domain;

import com.gmail.luisfpinho99.bysidefs.domain.model.group.UserGroup;
import com.gmail.luisfpinho99.bysidefs.domain.model.user.Password;
import com.gmail.luisfpinho99.bysidefs.domain.model.user.User;
import com.gmail.luisfpinho99.bysidefs.domain.model.user.Username;

public class UserBuilder {

    private Username username;
    private Password password;
    private UserGroup group;

    public UserBuilder withUsername(String username) {
        this.username = new Username(username);
        return this;
    }

    public UserBuilder withUsername(Username username) {
        this.username = username;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = new Password(password);
        return this;
    }

    public UserBuilder withGroup(UserGroup group) {
        this.group = group;
        return this;
    }

    public User build() {
        return new User(username, password, group);
    }
}
