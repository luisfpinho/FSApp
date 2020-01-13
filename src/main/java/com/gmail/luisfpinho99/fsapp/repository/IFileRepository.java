package com.gmail.luisfpinho99.fsapp.repository;

import com.gmail.luisfpinho99.fsapp.domain.model.entry.File;

public interface IFileRepository {

    File save(File file);

    File findById(String id);

    void remove(File file);
}
