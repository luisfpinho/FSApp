package com.gmail.luisfpinho99.bysidefs.ui.commands;

import com.gmail.luisfpinho99.bysidefs.domain.model.entry.Directory;
import com.gmail.luisfpinho99.bysidefs.domain.model.user.User;
import com.gmail.luisfpinho99.bysidefs.ui.Terminal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class HelpCommand extends Command {

    public static final String NAME = "help";

    public HelpCommand(Terminal terminal) {
        super(terminal, NAME);
    }

    @Override
    protected void run(User user, Directory currentDirectory, List<String> args, Set<CommandParameter> parameters) {
        if (args.size() == 0) {
            fullList();
        } else if (args.size() == 1) {
            specificHelp(args.get(0));
        } else {
            throw new IllegalArgumentException(NAME + " takes 0 or 1 arguments. If a command is indicated as an argument, information about that command will be displayed.");
        }
    }

    private void fullList() {
        List<String> result = new ArrayList<>();

        for (Command command : terminal.commands) {
            result.add(command.help());
        }

        Collections.sort(result);

        System.out.println();
        for (String command : result) {
            System.out.println(command);
        }
    }

    private void specificHelp(String commandName) {
        for (Command command : terminal.commands) {
            if (command.matches(commandName)) {
                System.out.println();
                System.out.println(command.help());
                return;
            }
        }

        throw new IllegalArgumentException(String.format("Command '%s' not found.%n", commandName));
    }
}
