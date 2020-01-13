package com.gmail.luisfpinho99.fsapp.domain.service;

import com.gmail.luisfpinho99.fsapp.domain.model.entry.Directory;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.Entry;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.EntryName;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.File;
import com.gmail.luisfpinho99.fsapp.domain.model.user.User;
import com.gmail.luisfpinho99.fsapp.repository.IDirectoryRepository;
import com.gmail.luisfpinho99.fsapp.repository.IFileRepository;
import com.gmail.luisfpinho99.fsapp.repository.PersistenceContext;

public class CopyEntryService {

    private IFileRepository fileRepository = PersistenceContext.repositories().files();
    private IDirectoryRepository directoryRepository = PersistenceContext.repositories().directories();

    public void copyEntry(User user, Directory currentDirectory, String originPath, String destinationPath, boolean keepOwner, boolean keepPermissions) {
        FindEntryService findEntryService = new FindEntryService();
        Entry origin = findEntryService.findEntryEnsureExists(user, currentDirectory, originPath);
        if (!origin.canBeReadBy(user)) {
            throw new IllegalArgumentException(String.format("No read permissions to copy entry '%s'.", originPath));
        }

        Directory destinationDirectory = findEntryService.findEntryParent(user, currentDirectory, destinationPath);
        if (!destinationDirectory.canBeWrittenBy(user)) {
            throw new IllegalArgumentException(String.format("No write permissions in destination directory to copy entry '%s'.", originPath));
        }

        Entry copy = origin.copy(destinationDirectory, EntryName.from(Entry.nameFromPath(destinationPath)), keepPermissions, keepOwner);
        save(copy, destinationDirectory);
    }

    private void save(Entry copy, Directory destinationDirectory) {
        if (copy instanceof File) {
            copy = fileRepository.save((File) copy);
        } else {
            copy = directoryRepository.save((Directory) copy);
        }

        try {
            destinationDirectory.addEntry(copy);
            directoryRepository.save(destinationDirectory);
        } catch (Exception e) {
            if (copy instanceof File) {
                fileRepository.remove((File) copy);
            } else {
                directoryRepository.remove((Directory) copy);
            }

            throw e;
        }
    }
}
