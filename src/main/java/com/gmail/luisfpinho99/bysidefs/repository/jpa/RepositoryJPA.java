package com.gmail.luisfpinho99.bysidefs.repository.jpa;

import javax.persistence.*;
import java.io.Serializable;

/**
 * An abstract JPA repository.
 *
 * @param <T> the type of the entity.
 * @param <I> the type of the primary key.
 */
public class RepositoryJPA<T, I extends Serializable> {

    private final Class<T> entityClass;
    private EntityManager entityManager;

    private static final String PERSISTENCE_UNIT_NAME = "JPAFSApp";

    @PersistenceUnit
    private static EntityManagerFactory entityManagerFactory;

    public RepositoryJPA(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T findById(I id) {
        return entityManager().find(entityClass, id);
    }

    public void remove(T entity) {
        try {
            entityManager().remove(entity);
        } catch (IllegalArgumentException e) {
            // Ignore since its already removed...
        }
    }

    public T save(final T entity) {
        EntityManager em = entityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        T obj = em.merge(entity);

        em.flush();
        em.refresh(obj);
        tx.commit();
        em.close();

        return obj;
    }

    @SuppressWarnings("unchecked")
    protected T singleObjectFromQuery(Query query) {
        try {
            return (T) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    protected EntityManager entityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = entityManagerFactory().createEntityManager();
        }

        return entityManager;
    }

    private static EntityManagerFactory entityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }

        return entityManagerFactory;
    }
}
