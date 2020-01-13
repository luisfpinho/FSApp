package com.gmail.luisfpinho99.fsapp.domain.service;

import com.gmail.luisfpinho99.fsapp.domain.model.entry.Directory;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.Entry;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.EntryName;
import com.gmail.luisfpinho99.fsapp.domain.model.user.User;
import com.gmail.luisfpinho99.fsapp.repository.IDirectoryRepository;
import com.gmail.luisfpinho99.fsapp.repository.PersistenceContext;

public class CreateDirectoryService {

    private final IDirectoryRepository directoryRepository = PersistenceContext.repositories().directories();

    public void createDirectory(User user, Directory currentDirectory, String path) {
        FindEntryService findEntryService = new FindEntryService();
        Directory parent = findEntryService.findEntryParent(user, currentDirectory, path);

        if (!parent.canBeWrittenBy(user)) {
            throw new IllegalArgumentException(String.format("No permissions to write in '%s'.", parent.getName()));
        }

        Directory newDirectory = new Directory(EntryName.from(Entry.nameFromPath(path)), parent, user);
        newDirectory = directoryRepository.save(newDirectory);

        // Not the best way, but currently fixes ID problems
        try {
            parent.addEntry(newDirectory);
            directoryRepository.save(parent);
        } catch (Exception e) {
            directoryRepository.remove(newDirectory);
            throw e;
        }
    }
}
