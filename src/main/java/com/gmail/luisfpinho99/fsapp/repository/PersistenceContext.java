package com.gmail.luisfpinho99.fsapp.repository;

import com.gmail.luisfpinho99.fsapp.repository.memory.RepositoryFactoryMem;

public final class PersistenceContext {

    private static IRepositoryFactory repositoryFactory = new RepositoryFactoryMem();

    /**
     * Private constructor to hide the implicit public one.
     */
    private PersistenceContext() {
        // Should be empty.
    }

    public static IRepositoryFactory repositories() {
        return repositoryFactory;
    }

    public static void init(IRepositoryFactory repositoryFactory) {
        PersistenceContext.repositoryFactory = repositoryFactory;
    }
}
