package com.gmail.luisfpinho99.fsapp.domain.service;

import com.gmail.luisfpinho99.fsapp.domain.model.entry.Directory;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.Entry;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.File;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.Permissions;
import com.gmail.luisfpinho99.fsapp.domain.model.user.User;
import com.gmail.luisfpinho99.fsapp.repository.IDirectoryRepository;
import com.gmail.luisfpinho99.fsapp.repository.IFileRepository;
import com.gmail.luisfpinho99.fsapp.repository.PersistenceContext;

public class ChangePermissionsService {

    private final IDirectoryRepository directoryRepository = PersistenceContext.repositories().directories();
    private final IFileRepository fileRepository = PersistenceContext.repositories().files();

    public void changePermissions(User user, Directory currentDirectory, String path, String newPermissions) {
        FindEntryService findEntryService = new FindEntryService();
        Entry entry = findEntryService.findEntryEnsureExists(user, currentDirectory, path);

        if (user.isRoot() && !entry.isOwner(user)) {
            throw new IllegalArgumentException("Only the owner of the entry can edit its permissions.");
        }

        Permissions permissions = Permissions.parse(newPermissions);
        entry.updatePermissions(permissions);

        if (entry instanceof File) {
            fileRepository.save((File) entry);
        } else {
            directoryRepository.save((Directory) entry);
        }
    }
}
