package com.gmail.luisfpinho99.bysidefs.repository.jpa;

import com.gmail.luisfpinho99.bysidefs.repository.*;

public class RepositoryFactoryJPA implements IRepositoryFactory {

    @Override
    public IDirectoryRepository directories() {
        return new DirectoryRepositoryJPA();
    }

    @Override
    public IUserRepository users() {
        return new UserRepositoryJPA();
    }

    @Override
    public IGroupRepository groups() {
        return new GroupRepositoryJPA();
    }

    @Override
    public IFileRepository files() {
        return new FileRepositoryJPA();
    }
}
