package com.gmail.luisfpinho99.fsapp.repository.memory;

import com.gmail.luisfpinho99.fsapp.domain.model.user.User;
import com.gmail.luisfpinho99.fsapp.domain.model.user.Username;
import com.gmail.luisfpinho99.fsapp.repository.IUserRepository;

public class UserRepositoryMem extends MemRepository<User, Username> implements IUserRepository {

    @Override
    public User findById(String username) {
        for (User user : entities) {
            if (user.getUsername().value().equalsIgnoreCase(username)) {
                return user;
            }
        }

        return null;
    }

    @Override
    protected Username getId(User user) {
        return user.getUsername();
    }
}
