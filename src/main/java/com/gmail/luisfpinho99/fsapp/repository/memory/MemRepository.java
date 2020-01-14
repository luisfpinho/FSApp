package com.gmail.luisfpinho99.fsapp.repository.memory;

import java.util.HashSet;
import java.util.Set;

public abstract class MemRepository<T, I> {

    protected Set<T> entities;

    public MemRepository() {
        entities = new HashSet<>();
    }

    public T save(T entity) {
        entities.add(entity);
        return entity;
    }

    public void remove(T entity) {
        entities.remove(entity);
    }

    public T findById(I id) {
        for (T entity : entities) {
            if (getId(entity).equals(id)) {
                return entity;
            }
        }

        return null;
    }

    protected abstract I getId(T entity);
}
