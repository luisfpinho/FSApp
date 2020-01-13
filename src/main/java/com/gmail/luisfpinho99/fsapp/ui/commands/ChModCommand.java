package com.gmail.luisfpinho99.fsapp.ui.commands;

import com.gmail.luisfpinho99.fsapp.domain.model.entry.Directory;
import com.gmail.luisfpinho99.fsapp.domain.model.user.User;
import com.gmail.luisfpinho99.fsapp.domain.service.ChangePermissionsService;
import com.gmail.luisfpinho99.fsapp.ui.Terminal;

import java.util.List;
import java.util.Set;

public class ChModCommand extends Command {

    private static final String NAME = "chmod";

    public ChModCommand(Terminal terminal) {
        super(terminal, NAME);
    }

    @Override
    protected void run(User user, Directory currentDirectory, List<String> args, Set<CommandParameter> parameters) {
        if (args.size() != 2) {
            throw new IllegalArgumentException(NAME + " takes 2 arguments: the path of an entry and the new permissions as a series of 9 binary digits. (ex: chmod myfolder 111100100)");
        }

        ChangePermissionsService changePermissionsService = new ChangePermissionsService();
        changePermissionsService.changePermissions(user, currentDirectory, args.get(0), args.get(1));

        System.out.println("Permissions changed.");
    }
}
