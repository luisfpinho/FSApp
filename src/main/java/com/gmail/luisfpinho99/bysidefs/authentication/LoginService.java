package com.gmail.luisfpinho99.bysidefs.authentication;

import com.gmail.luisfpinho99.bysidefs.domain.model.user.User;
import com.gmail.luisfpinho99.bysidefs.repository.IUserRepository;
import com.gmail.luisfpinho99.bysidefs.repository.PersistenceContext;

public class LoginService {

    private final IUserRepository userRepository = PersistenceContext.repositories().users();

    public User login(String username, String password) {
        User user = userRepository.findById(username);
        if (user == null) {
            return null;
        }

        if (user.authenticate(password)) {
            return user;
        } else {
            throw new IllegalArgumentException("Wrong password.");
        }
    }
}
