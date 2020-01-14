package com.gmail.luisfpinho99.fsapp.repository.memory;

import com.gmail.luisfpinho99.fsapp.repository.*;

public class RepositoryFactoryMem implements IRepositoryFactory {

    private IDirectoryRepository directoryRepository;
    private IFileRepository fileRepository;
    private IGroupRepository groupRepository;
    private IUserRepository userRepository;

    public RepositoryFactoryMem() {
        directoryRepository = new DirectoryRepositoryMem();
        fileRepository = new FileRepositoryMem();
        groupRepository = new GroupRepositoryMem();
        userRepository = new UserRepositoryMem();
    }

    @Override
    public IDirectoryRepository directories() {
        return directoryRepository;
    }

    @Override
    public IUserRepository users() {
        return userRepository;
    }

    @Override
    public IGroupRepository groups() {
        return groupRepository;
    }

    @Override
    public IFileRepository files() {
        return fileRepository;
    }
}
