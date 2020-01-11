package com.gmail.luisfpinho99.bysidefs.repository.jpa;

import com.gmail.luisfpinho99.bysidefs.domain.model.entry.File;
import com.gmail.luisfpinho99.bysidefs.repository.IFileRepository;

public class FileRepositoryJPA extends RepositoryJPA<File, String> implements IFileRepository {

    public FileRepositoryJPA() {
        super(File.class);
    }
}
