package com.gmail.luisfpinho99.bysidefs.domain.service;

import com.gmail.luisfpinho99.bysidefs.domain.model.entry.Directory;
import com.gmail.luisfpinho99.bysidefs.domain.model.entry.Entry;
import com.gmail.luisfpinho99.bysidefs.domain.model.user.User;
import com.gmail.luisfpinho99.bysidefs.repository.IDirectoryRepository;
import com.gmail.luisfpinho99.bysidefs.repository.IFileRepository;
import com.gmail.luisfpinho99.bysidefs.repository.PersistenceContext;

public class FindEntryService {

    private final IDirectoryRepository directoryRepository = PersistenceContext.repositories().directories();
    private final IFileRepository fileRepository = PersistenceContext.repositories().files();

    public Entry findEntryEnsureExists(User user, Directory currentDirectory, String path) {
        Entry entry = findEntry(user, currentDirectory, path);
        if (entry == null) {
            throw new IllegalArgumentException(String.format("The entry '%s' was not found.", path));
        }

        return entry;
    }

    public Entry findEntry(User user, String path) {
        return findEntry(user, null, path);
    }

    public Entry findEntry(User user, Directory currentDirectory, String path) {
        path = path.trim();
        if (path.equals(".")) {
            return fetch(currentDirectory);
        }

        validatePath(path);

        if (path.charAt(0) == Directory.SEPARATOR || currentDirectory == null) {
            currentDirectory = directoryRepository.findRoot();
        }

        String[] parts = path.trim().split(String.valueOf(Directory.SEPARATOR));

        // Start by the current directory and search its children and do the same with the next intermediate
        // directories.
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (part.equalsIgnoreCase(Directory.PARENT_DIRECTORY)) {
                currentDirectory = findParent(currentDirectory);
                continue;
            }

            // If the last level was reached, return the entry it refers to.
            // Otherwise, look for the next directory in the current directory.
            if (i == parts.length - 1) {
                return fetch(currentDirectory.findChild(part));
            } else {
                currentDirectory = findDirectory(user, currentDirectory, part);
            }
        }

        return fetch(currentDirectory);
    }

    public Directory findEntryParent(User user, Directory currentDirectory, String path) {
        int indexOfSeparator = path.lastIndexOf(Directory.SEPARATOR);
        if (indexOfSeparator != -1) {
            path = path.substring(0, indexOfSeparator);
        } else {
            return currentDirectory;
        }

        Entry entry = findEntry(user, currentDirectory, path);
        if (!(entry instanceof Directory)) {
            throw new IllegalArgumentException(String.format("'%s' is not a directory.", path));
        }

        return (Directory) entry;
    }

    private Directory findDirectory(User user, Directory currentDirectory, String name) {
        Entry child = findChild(currentDirectory, name);

        if (!(child instanceof Directory)) {
            throw new IllegalArgumentException(String.format("'%s' is not a directory.", child.absolutePath()));
        }

        if (!child.canBeExecutedBy(user)) {
            throw new IllegalArgumentException(String.format("No permissions to open directory '%s'", child.absolutePath()));
        }

        return (Directory) child;
    }

    private Entry fetch(Entry entry) {
        if (entry == null || entry.getId() == null) {
            return entry;
        }

        if (entry instanceof Directory) {
            return directoryRepository.findById(entry.getId());
        } else {
            return fileRepository.findById(entry.getId());
        }
    }

    private static Entry findChild(Directory directory, String name) {
        Entry child = directory.findChild(name);
        if (child == null) {
            throw new IllegalArgumentException(
                    String.format("The directory '%s' does not exist.", directory.pathWithChild(name))
            );
        }

        return child;
    }

    private static Directory findParent(Entry entry) {
        if (entry.isRoot()) {
            throw new IllegalArgumentException("The root entry has no parent directory.");
        }

        return entry.getParent();
    }

    private static void validatePath(String path) {
        if (!path.matches(Directory.SEPARATOR + "?([a-zA-ZÀ-ÖØ-öø-ÿ0-9_+=():&#!%$\\s]+|\\.\\.|" + Directory.SEPARATOR + ")+")) {
            throw new IllegalArgumentException(String.format("The path '%s' is invalid.", path));
        }
    }
}
