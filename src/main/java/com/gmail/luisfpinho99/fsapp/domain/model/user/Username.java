package com.gmail.luisfpinho99.fsapp.domain.model.user;

import com.gmail.luisfpinho99.fsapp.Preconditions;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Username implements Serializable {

    private final String usernameValue;

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 10;

    public Username(String username) {
        Preconditions.ensureNotNull("The username cannot be null.", username);
        Preconditions.ensureBetween(
                String.format("The length of the username must be between %d and %d.", MIN_LENGTH, MAX_LENGTH),
                MIN_LENGTH,
                MAX_LENGTH,
                username.length()
        );
        Preconditions.ensure("The username cannot contain spaces.", !username.contains(" "));

        this.usernameValue = username;
    }

    protected Username() {
        // Need to initialize final fields.
        this.usernameValue = null;
    }

    @Override
    public String toString() {
        return usernameValue;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Username)) {
            return false;
        }

        Username otherUsername = (Username) other;
        return usernameValue.equalsIgnoreCase(otherUsername.usernameValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usernameValue.toLowerCase());
    }

    public String value() {
        return usernameValue;
    }

    public static Username from(String username) {
        return new Username(username);
    }
}
