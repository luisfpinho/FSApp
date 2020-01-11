package com.gmail.luisfpinho99.bysidefs.repository;

import com.gmail.luisfpinho99.bysidefs.domain.model.entry.Directory;

public interface IDirectoryRepository {

    Directory findRoot();

    Directory findById(String id);

    Directory save(Directory directory);

    void remove(Directory id);
}
