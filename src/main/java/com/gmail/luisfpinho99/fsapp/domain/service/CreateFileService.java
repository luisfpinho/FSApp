package com.gmail.luisfpinho99.fsapp.domain.service;

import com.gmail.luisfpinho99.fsapp.domain.model.entry.Directory;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.Entry;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.EntryName;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.File;
import com.gmail.luisfpinho99.fsapp.domain.model.user.User;
import com.gmail.luisfpinho99.fsapp.repository.IDirectoryRepository;
import com.gmail.luisfpinho99.fsapp.repository.IFileRepository;
import com.gmail.luisfpinho99.fsapp.repository.PersistenceContext;

public class CreateFileService {

    private final IFileRepository fileRepository = PersistenceContext.repositories().files();
    private final IDirectoryRepository directoryRepository = PersistenceContext.repositories().directories();

    public void createFile(User user, Directory currentDirectory, String path) {
        FindEntryService findEntryService = new FindEntryService();
        Directory parent = findEntryService.findEntryParent(user, currentDirectory, path);

        if (!parent.canBeWrittenBy(user)) {
            throw new IllegalArgumentException(
                    String.format("No permissions to write in '%s'.", parent.absolutePath()));
        }

        File file = new File(EntryName.from(Entry.nameFromPath(path)), parent, user);
        file = fileRepository.save(file);

        try {
            parent.addEntry(file);
            directoryRepository.save(parent);
        } catch (Exception e) {
            fileRepository.remove(file);
            throw e;
        }
    }
}
