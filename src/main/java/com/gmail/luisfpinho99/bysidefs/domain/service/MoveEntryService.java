package com.gmail.luisfpinho99.bysidefs.domain.service;

import com.gmail.luisfpinho99.bysidefs.domain.model.entry.Directory;
import com.gmail.luisfpinho99.bysidefs.domain.model.entry.Entry;
import com.gmail.luisfpinho99.bysidefs.domain.model.entry.EntryName;
import com.gmail.luisfpinho99.bysidefs.domain.model.entry.File;
import com.gmail.luisfpinho99.bysidefs.domain.model.user.User;
import com.gmail.luisfpinho99.bysidefs.repository.IDirectoryRepository;
import com.gmail.luisfpinho99.bysidefs.repository.IFileRepository;
import com.gmail.luisfpinho99.bysidefs.repository.PersistenceContext;

public class MoveEntryService {

    private IFileRepository fileRepository = PersistenceContext.repositories().files();
    private IDirectoryRepository directoryRepository = PersistenceContext.repositories().directories();

    public void moveEntryService(User user, Directory currentDirectory, String originPath, String destinationPath, boolean keepOwner, boolean keepPermissions) {
        FindEntryService findEntryService = new FindEntryService();
        Entry origin = findEntryService.findEntryEnsureExists(user, currentDirectory, originPath);
        if (!origin.canBeReadBy(user)) {
            throw new IllegalArgumentException(String.format("No read permissions to move entry '%s'.", originPath));
        }

        Directory destinationDirectory = findEntryService.findEntryParent(user, currentDirectory, destinationPath);
        if (!destinationDirectory.canBeWrittenBy(user)) {
            throw new IllegalArgumentException(String.format("No write permissions in destination directory to move entry '%s'.", originPath));
        }

        origin.updateName(EntryName.from(Entry.nameFromPath(destinationPath)));

        Directory originDirectory = origin.getParent();
        origin = saveEntry(origin);

        if (!originDirectory.equals(destinationDirectory)) {
            originDirectory.removeEntry(origin);
            save(origin, originDirectory, destinationDirectory, keepOwner, keepPermissions);
        } else {
            saveEntry(originDirectory);
        }
    }

    private void save(Entry origin, Directory originDirectory, Directory destinationDirectory, boolean keepOwner, boolean keepPermissions) {
        try {
            destinationDirectory.addEntry(origin, keepPermissions, keepOwner);
            directoryRepository.save(destinationDirectory);
            directoryRepository.save(originDirectory);
        } catch (Exception e) {
            if (origin instanceof Directory) {
                directoryRepository.remove((Directory) origin);
            } else {
                fileRepository.remove((File) origin);
            }
            throw e;
        }
    }

    private Entry saveEntry(Entry entry) {
        if (entry instanceof Directory) {
            return directoryRepository.save((Directory) entry);
        } else {
            return fileRepository.save((File) entry);
        }
    }
}
