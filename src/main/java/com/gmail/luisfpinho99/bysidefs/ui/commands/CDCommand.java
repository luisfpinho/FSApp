package com.gmail.luisfpinho99.bysidefs.ui.commands;

import com.gmail.luisfpinho99.bysidefs.domain.model.entry.Directory;
import com.gmail.luisfpinho99.bysidefs.domain.model.user.User;
import com.gmail.luisfpinho99.bysidefs.domain.service.OpenDirectoryService;
import com.gmail.luisfpinho99.bysidefs.ui.Terminal;

import java.util.List;
import java.util.Set;

public class CDCommand extends Command {

    public CDCommand(Terminal terminal) {
        super(terminal, "cd");
    }

    @Override
    protected void run(User user, Directory currentDirectory, List<String> args, Set<CommandParameter> parameters) {
        OpenDirectoryService openDirectoryService = new OpenDirectoryService();
        Directory newDirectory = openDirectoryService.openDirectory(user, currentDirectory, joinArgs(args));
        terminal.changeCurrentDirectory(newDirectory);
    }
}
