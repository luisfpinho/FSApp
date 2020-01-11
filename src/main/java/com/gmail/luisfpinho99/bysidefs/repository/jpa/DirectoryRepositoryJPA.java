package com.gmail.luisfpinho99.bysidefs.repository.jpa;

import com.gmail.luisfpinho99.bysidefs.domain.model.entry.Directory;
import com.gmail.luisfpinho99.bysidefs.repository.IDirectoryRepository;

import javax.persistence.Query;

public class DirectoryRepositoryJPA extends RepositoryJPA<Directory, String> implements IDirectoryRepository {

    public DirectoryRepositoryJPA() {
        super(Directory.class);
    }

    @Override
    public Directory findRoot() {
        Query query = entityManager().createQuery("SELECT D FROM Directory D WHERE D.parent = NULL");
        return singleObjectFromQuery(query);
    }
}
