package com.gmail.luisfpinho99.fsapp.domain.service;

import com.gmail.luisfpinho99.fsapp.domain.model.entry.Directory;
import com.gmail.luisfpinho99.fsapp.domain.model.user.User;
import com.gmail.luisfpinho99.fsapp.repository.IDirectoryRepository;
import com.gmail.luisfpinho99.fsapp.repository.PersistenceContext;

public class UpdateCurrentDirectoryService {

    private final IDirectoryRepository directoryRepository = PersistenceContext.repositories().directories();

    public Directory updateCurrentDirectory(User user, Directory currentDirectory) {
        currentDirectory = directoryRepository.findById(currentDirectory.getId());

        while (!currentDirectory.isRoot() && !currentDirectory.canBeExecutedBy(user)) {
            currentDirectory = currentDirectory.getParent();
        }

        return currentDirectory;
    }
}
