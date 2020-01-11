package com.gmail.luisfpinho99.bysidefs.ui.commands;

import com.gmail.luisfpinho99.bysidefs.domain.model.entry.Directory;
import com.gmail.luisfpinho99.bysidefs.domain.model.user.User;
import com.gmail.luisfpinho99.bysidefs.domain.service.CreateFileService;
import com.gmail.luisfpinho99.bysidefs.domain.service.EditFileService;
import com.gmail.luisfpinho99.bysidefs.ui.Terminal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TouchCommand extends Command {

    private static final String NAME = "touch";
    private static final Map<String, Boolean> ACCEPTED_PARAMS = createAcceptedParameters();

    public TouchCommand(Terminal terminal) {
        super(terminal, NAME, ACCEPTED_PARAMS);
    }

    @Override
    protected void run(User user, Directory currentDirectory, List<String> args, Set<CommandParameter> parameters) {
        String argsString = joinArgs(args);
        String content = null;
        if (args.size() > 2 && args.contains("<") && args.indexOf("<") != args.size() - 1) {
            int index = argsString.indexOf("<");
            content = argsString.substring(index + 2);
            argsString = argsString.substring(0, index - 1);
        }

        CreateFileService createFileService = new CreateFileService();
        createFileService.createFile(user, currentDirectory, argsString);

        if (content != null) {
            EditFileService editFileService = new EditFileService();
            editFileService.editFile(user, currentDirectory, argsString, content);
        }

        System.out.printf("The file '%s' was created.%n", argsString);
    }

    private static Map<String, Boolean> createAcceptedParameters() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("p", true);
        return map;
    }
}
