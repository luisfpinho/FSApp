package com.gmail.luisfpinho99.fsapp.ui.commands;

import com.gmail.luisfpinho99.fsapp.domain.model.entry.Directory;
import com.gmail.luisfpinho99.fsapp.domain.model.user.User;
import com.gmail.luisfpinho99.fsapp.domain.service.CreateDirectoryService;
import com.gmail.luisfpinho99.fsapp.ui.Terminal;

import java.util.List;
import java.util.Set;

public class MkDirCommand extends Command {

    public MkDirCommand(Terminal terminal) {
        super(terminal, "mkdir");
    }

    @Override
    protected void run(User user, Directory currentDirectory, List<String> args, Set<CommandParameter> parameters) {
        CreateDirectoryService createDirectoryService = new CreateDirectoryService();
        createDirectoryService.createDirectory(user, currentDirectory, joinArgs(args));

        System.out.printf("Directory '%s' created.%n", joinArgs(args));
    }
}
