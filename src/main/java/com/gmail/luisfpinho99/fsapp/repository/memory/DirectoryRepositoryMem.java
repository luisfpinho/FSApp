package com.gmail.luisfpinho99.fsapp.repository.memory;

import com.gmail.luisfpinho99.fsapp.domain.model.entry.Directory;
import com.gmail.luisfpinho99.fsapp.repository.IDirectoryRepository;

public class DirectoryRepositoryMem extends MemRepository<Directory, String> implements IDirectoryRepository {

    @Override
    public Directory findRoot() {
        for (Directory directory : entities) {
            if (directory.isRoot()) {
                return directory;
            }
        }

        return null;
    }

    @Override
    protected String getId(Directory entity) {
        return entity.getId();
    }

    @Override
    public Directory save(Directory directory) {
        directory.setId(directory.absolutePath());
        return super.save(directory);
    }
}
