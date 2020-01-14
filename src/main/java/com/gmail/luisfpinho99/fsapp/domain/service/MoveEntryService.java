package com.gmail.luisfpinho99.fsapp.domain.service;

import com.gmail.luisfpinho99.fsapp.domain.model.entry.Directory;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.Entry;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.EntryName;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.File;
import com.gmail.luisfpinho99.fsapp.domain.model.user.User;
import com.gmail.luisfpinho99.fsapp.repository.IDirectoryRepository;
import com.gmail.luisfpinho99.fsapp.repository.IFileRepository;
import com.gmail.luisfpinho99.fsapp.repository.PersistenceContext;

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

        EntryName originalName = origin.getName();
        origin.updateName(EntryName.from(Entry.nameFromPath(destinationPath)));

        Directory originDirectory = origin.getParent();
        origin = saveEntry(origin);

        originDirectory.removeEntry(origin);
        save(originalName, origin, originDirectory, destinationDirectory, keepOwner, keepPermissions);
    }

    private void save(EntryName originalName, Entry origin, Directory originDirectory, Directory destinationDirectory, boolean keepOwner, boolean keepPermissions) {
        try {
            destinationDirectory.addEntry(origin, keepPermissions, keepOwner);
//            directoryRepository.save(destinationDirectory);
//            directoryRepository.save(originDirectory);
        } catch (Exception e) {
            origin.updateName(originalName);
//            if (origin instanceof Directory) {
//                directoryRepository.remove((Directory) origin);
//            } else {
//                fileRepository.remove((File) origin);
//            }
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
