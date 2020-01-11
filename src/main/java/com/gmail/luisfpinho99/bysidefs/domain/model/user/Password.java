package com.gmail.luisfpinho99.bysidefs.domain.model.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.gmail.luisfpinho99.bysidefs.Preconditions;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Password implements Serializable {

    private final byte[] hash;

    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 20;

    public Password(String rawPassword) {
        Preconditions.ensureNotNull("The password cannot be null.", rawPassword);
        Preconditions.ensureBetween(
                String.format("The password must be between %d and %d characters long.", MIN_LENGTH, MAX_LENGTH),
                MIN_LENGTH,
                MAX_LENGTH,
                rawPassword.length()
        );

        hash = encrypt(rawPassword);
    }

    /**
     * Only for ORM.
     */
    protected Password() {
        // Need to initialize final fields
        hash = null;
    }

    public boolean authenticate(String attempt) {
        return BCrypt.verifyer().verify(attempt.toCharArray(), hash).verified;
    }

    private static byte[] encrypt(String rawPassword) {
        return BCrypt.withDefaults().hash(6, rawPassword.toCharArray());
    }
}
