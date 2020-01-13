package com.gmail.luisfpinho99.fsapp.repository;

public interface IRepositoryFactory {

    IDirectoryRepository directories();

    IUserRepository users();

    IGroupRepository groups();

    IFileRepository files();
}
