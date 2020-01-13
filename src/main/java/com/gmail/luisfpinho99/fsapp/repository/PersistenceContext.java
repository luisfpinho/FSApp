package com.gmail.luisfpinho99.fsapp.repository;

import com.gmail.luisfpinho99.fsapp.repository.jpa.RepositoryFactoryJPA;

public final class PersistenceContext {

    /**
     * Private constructor to hide the implicit public one.
     */
    private PersistenceContext() {
        // Should be empty.
    }

    public static IRepositoryFactory repositories() {
        return new RepositoryFactoryJPA();
    }
}