package com.gmail.luisfpinho99.bysidefs.repository;

import com.gmail.luisfpinho99.bysidefs.domain.model.user.User;
import com.gmail.luisfpinho99.bysidefs.domain.model.user.Username;

public interface IUserRepository {

    User save(User user);

    User findById(Username username);

    User findById(String username);
}
