package com.gmail.luisfpinho99.bysidefs.domain.service;

import com.gmail.luisfpinho99.bysidefs.domain.model.entry.Directory;
import com.gmail.luisfpinho99.bysidefs.domain.model.entry.Entry;
import com.gmail.luisfpinho99.bysidefs.domain.model.user.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class LongListChildrenService {

    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    public List<String> longListChildren(User user, Directory directory) {
        if (!directory.canBeReadBy(user)) {
            throw new IllegalArgumentException(
                    String.format("No permissions to read directory '%s'.", directory.getName()));
        }

        List<String> result = new ArrayList<>();

        for (Entry child : directory.getChildren()) {
            String description = formatEntry(child);
            result.add(description);
        }

        if (result.size() == 1) {
            result.add("(empty)");
        } else {
            Collections.sort(result);
        }

        // Add to begining
        result.listIterator().add("\nls " + formatEntry(directory));
        return result;
    }

    private static String formatEntry(Entry child) {
        String entryType;
        if (child instanceof Directory) {
            entryType = "d";
        } else {
            entryType = "f";
        }

        return String.format("%s %-16s %-6d %-10s %-7s %s %s", entryType, child.getName(), child.getSize(),
                child.getPermissions(), child.getOwner().getUsername(), formatDate(child.getCreationTime().asDate()),
                formatDate(child.getLastModificationTime().asDate()));
    }

    private static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(date);
    }
}
