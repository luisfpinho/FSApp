package com.gmail.luisfpinho99.fsapp.repository;

import com.gmail.luisfpinho99.fsapp.domain.model.user.User;
import com.gmail.luisfpinho99.fsapp.domain.model.user.Username;

public interface IUserRepository {

    User save(User user);

    User findById(Username username);

    User findById(String username);
}
