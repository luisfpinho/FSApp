package com.gmail.luisfpinho99.fsapp.repository.memory;

import com.gmail.luisfpinho99.fsapp.domain.model.entry.File;
import com.gmail.luisfpinho99.fsapp.repository.IFileRepository;

public class FileRepositoryMem extends MemRepository<File, String> implements IFileRepository {

    @Override
    protected String getId(File entity) {
        return entity.getId();
    }
}
