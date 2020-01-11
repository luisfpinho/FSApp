package com.gmail.luisfpinho99.bysidefs.repository;

public interface IRepositoryFactory {

    IDirectoryRepository directories();

    IUserRepository users();

    IGroupRepository groups();

    IFileRepository files();
}
