package com.gmail.luisfpinho99.bysidefs.domain.service;

import com.gmail.luisfpinho99.bysidefs.domain.model.entry.Directory;
import com.gmail.luisfpinho99.bysidefs.domain.model.entry.Entry;
import com.gmail.luisfpinho99.bysidefs.domain.model.user.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListChildrenService {

    public List<String> listChildren(User user, Directory directory) {
        if (!directory.canBeReadBy(user)) {
            throw new IllegalArgumentException(String.format("No permissions to read directory '%s'.", directory.getName()));
        }

        List<String> result = new ArrayList<>();

        for (Entry child : directory.getChildren()) {
            String entryType;
            if (child instanceof Directory) {
                entryType = "d";
            } else {
                entryType = "f";
            }

            String description = String.format("%s %s", entryType, child.getName());
            result.add(description);
        }

        Collections.sort(result);
        return result;
    }
}
