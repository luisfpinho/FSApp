package com.gmail.luisfpinho99.fsapp.domain.service;

import com.gmail.luisfpinho99.fsapp.domain.model.entry.Directory;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.Entry;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.File;
import com.gmail.luisfpinho99.fsapp.domain.model.user.User;

public class ShowFileContentService {

    public String showFileContent(User user, Directory currentDirectory, String path) {
        FindEntryService findEntryService = new FindEntryService();
        Entry entry = findEntryService.findEntryEnsureExists(user, currentDirectory, path);
        if (!(entry instanceof File)) {
            throw new IllegalArgumentException(String.format("'%s' is not a file.", entry.absolutePath()));
        }

        if (!entry.canBeReadBy(user)) {
            throw new IllegalArgumentException(
                    String.format("No permissions to read the content of '%s'.", entry.absolutePath()));
        }

        File file = (File) entry;
        return file.getContent().value();
    }
}
