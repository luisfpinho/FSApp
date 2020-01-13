package com.gmail.luisfpinho99.fsapp.repository.jpa;

import com.gmail.luisfpinho99.fsapp.domain.model.user.User;
import com.gmail.luisfpinho99.fsapp.domain.model.user.Username;
import com.gmail.luisfpinho99.fsapp.repository.IUserRepository;

import javax.persistence.Query;

public class UserRepositoryJPA extends RepositoryJPA<User, Username> implements IUserRepository {

    public UserRepositoryJPA() {
        super(User.class);
    }

    @Override
    public User findById(String username) {
        Query query = entityManager().createQuery("SELECT u FROM User u WHERE u.username.usernameValue = :x");
        query.setParameter("x", username);
        return singleObjectFromQuery(query);
    }
}
