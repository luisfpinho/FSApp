package com.gmail.luisfpinho99.fsapp.repository;

import com.gmail.luisfpinho99.fsapp.domain.model.entry.Directory;

public interface IDirectoryRepository {

    Directory findRoot();

    Directory findById(String id);

    Directory save(Directory directory);

    void remove(Directory id);
}
