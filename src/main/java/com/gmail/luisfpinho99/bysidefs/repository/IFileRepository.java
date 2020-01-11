package com.gmail.luisfpinho99.bysidefs.repository;

import com.gmail.luisfpinho99.bysidefs.domain.model.entry.File;

public interface IFileRepository {

    File save(File file);

    File findById(String id);

    void remove(File file);
}
