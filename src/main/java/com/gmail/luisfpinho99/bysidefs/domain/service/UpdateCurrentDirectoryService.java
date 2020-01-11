package com.gmail.luisfpinho99.bysidefs.domain.service;

import com.gmail.luisfpinho99.bysidefs.domain.model.entry.Directory;
import com.gmail.luisfpinho99.bysidefs.domain.model.user.User;
import com.gmail.luisfpinho99.bysidefs.repository.IDirectoryRepository;
import com.gmail.luisfpinho99.bysidefs.repository.PersistenceContext;

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
