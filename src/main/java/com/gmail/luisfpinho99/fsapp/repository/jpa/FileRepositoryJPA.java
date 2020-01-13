package com.gmail.luisfpinho99.fsapp.repository.jpa;

import com.gmail.luisfpinho99.fsapp.domain.model.entry.File;
import com.gmail.luisfpinho99.fsapp.repository.IFileRepository;

public class FileRepositoryJPA extends RepositoryJPA<File, String> implements IFileRepository {

    public FileRepositoryJPA() {
        super(File.class);
    }
}
