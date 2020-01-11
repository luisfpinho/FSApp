package com.gmail.luisfpinho99.bysidefs.ui.commands;

import com.gmail.luisfpinho99.bysidefs.domain.model.entry.Directory;
import com.gmail.luisfpinho99.bysidefs.domain.model.user.User;
import com.gmail.luisfpinho99.bysidefs.domain.service.ListChildrenService;
import com.gmail.luisfpinho99.bysidefs.domain.service.LongListChildrenService;
import com.gmail.luisfpinho99.bysidefs.ui.Terminal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LSCommand extends Command {

    private static final Map<String, Boolean> acceptedParameters = createAcceptedParameters();
    private static final String LONG_LIST_PARAMETER = "l";

    public LSCommand(Terminal terminal) {
        super(terminal, "ls", acceptedParameters);
    }

    @Override
    protected void run(User user, Directory currentDirectory, List<String> args, Set<CommandParameter> parameters) {
        if (containsParameter(parameters, LONG_LIST_PARAMETER)) {
            longListing(user, currentDirectory);
        } else {
            simpleListing(user, currentDirectory);
        }
    }

    private void simpleListing(User user, Directory currentDirectory) {
        ListChildrenService listChildrenService = new ListChildrenService();
        List<String> childrenNames = listChildrenService.listChildren(user, currentDirectory);

        System.out.printf("%nls %s%n", currentDirectory.absolutePath());
        for (String childName : childrenNames) {
            System.out.println(childName);
        }

        if (childrenNames.isEmpty()) {
            System.out.println("(empty)");
        }
    }

    private void longListing(User user, Directory currentDirectory) {
        LongListChildrenService longListChildrenService = new LongListChildrenService();
        List<String> children = longListChildrenService.longListChildren(user, currentDirectory);

        for (String child : children) {
            System.out.println(child);
        }
    }

    private static Map<String, Boolean> createAcceptedParameters() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("l", false);
        return map;
    }
}
