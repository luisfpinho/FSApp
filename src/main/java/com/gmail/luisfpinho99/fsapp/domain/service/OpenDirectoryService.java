package com.gmail.luisfpinho99.fsapp.domain.service;

import com.gmail.luisfpinho99.fsapp.domain.model.entry.Directory;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.Entry;
import com.gmail.luisfpinho99.fsapp.domain.model.user.User;

public class OpenDirectoryService {

    public Directory openDirectory(User user, Directory currentDirectory, String path) {
        FindEntryService findEntryService = new FindEntryService();
        Entry entry = findEntryService.findEntryEnsureExists(user, currentDirectory, path);
        if (!(entry instanceof Directory)) {
            throw new IllegalArgumentException(String.format("'%s' is not a directory.", path));
        }

        if (!entry.canBeExecutedBy(user)) {
            throw new IllegalArgumentException(String.format("No permissions to open '%s'.", entry.absolutePath()));
        }

        return (Directory) entry;
    }
}
