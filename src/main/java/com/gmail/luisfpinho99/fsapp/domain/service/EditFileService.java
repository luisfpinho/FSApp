package com.gmail.luisfpinho99.fsapp.domain.service;

import com.gmail.luisfpinho99.fsapp.domain.model.entry.Directory;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.Entry;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.File;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.FileContent;
import com.gmail.luisfpinho99.fsapp.domain.model.user.User;
import com.gmail.luisfpinho99.fsapp.repository.IFileRepository;
import com.gmail.luisfpinho99.fsapp.repository.PersistenceContext;

public class EditFileService {

    private final IFileRepository fileRepository = PersistenceContext.repositories().files();

    public void editFile(User user, Directory currentDirectory, String path, String newContent) {
        FindEntryService findEntryService = new FindEntryService();
        Entry entry = findEntryService.findEntryEnsureExists(user, currentDirectory, path);
        editFile(user, entry, newContent);
    }

    public void editFile(User user, Entry entry, String newContent) {
        if (!(entry instanceof File)) {
            throw new IllegalArgumentException(String.format("The entry '%s' is not a file.", entry.getName()));
        }

        if (!entry.canBeWrittenBy(user)) {
            throw new IllegalArgumentException(
                    String.format("The user does not have write permission to edit the file '%s'.", entry.getName()));
        }

        File file = (File) entry;
        FileContent newFileContent = new FileContent(newContent);
        file.updateContent(newFileContent);
         fileRepository.save(file);
    }
}
